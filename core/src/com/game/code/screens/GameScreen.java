package com.game.code.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.code.Application;
import com.game.code.EntityBuilding.Summoners.SummonerType;
import com.game.code.EntityBuilding.battlefiled.*;
import com.game.code.Socket.*;
import com.game.code.components.SummonsComponent;
import com.game.code.components.TankTemplateComponent;
import com.game.code.components.TextComponent;
import com.game.code.screens.Loading.TaskLoader;
import com.game.code.systems.Death.DamagingSystem;
import com.game.code.systems.HUD.HealthMeterSystem;
import com.game.code.systems.HUD.ScoreDisplaySystem;
import com.game.code.systems.HUD.ScoreSystem;
import com.game.code.systems.HUD.TimeSystem;
import com.game.code.utils.BoundedCamera;
import com.game.code.utils.Bounds;
import com.game.code.utils.MatchTime;
import com.game.code.utils.Score;
import com.game.code.utils.TweenUtils.TweenM;
import io.socket.client.Ack;
import io.socket.client.Socket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;


public class GameScreen extends EngineScreen {

    private final String roomId;

    private final GameLobbyScreen gameLobbyScreen;

    private final GameSettings gameSettings = new GameSettings();

    private Random random;

    private Bounds bounds;
    private Bounds innerBounds;
    public GameScreen(Application app, String roomId, GameLobbyScreen gameLobbyScreen) {
        super(app);
        this.roomId = roomId;
        this.gameLobbyScreen = gameLobbyScreen;
    }

    @Override
    public TaskLoader getLoadingTask() {
        return TaskLoader.create()
                .loadFromSocket(app.getSocket("/inRoom"), "getGameSettings",
                        this::initGameSettings,
                        new JSONObject(Map.of("roomId", roomId)))
                .add(this::initBounds, "bounds")
                .add(super.getLoadingTask())
                .add(this::initSocket, "socket")
                .get();
    }

    private void initGameSettings(Object... args) {
        try{
            JSONObject data = (JSONObject) args[0];
            JSONObject gameSettingsData = data.getJSONObject("gameSettings");
            int seed = data.getInt("seed");

            random = new Random(seed);
            new SocketGameSettingsParser(gameSettings).parseGameSettingsData(gameSettingsData);

        } catch (JSONException e) {
            Gdx.app.log("InitGameSettingsError", "Failed reading gameSettingsData");
        }
    }

    private void initBounds() {
        float width = gameSettings.otherSettings().get("width", 15);
        float height = gameSettings.otherSettings().get("height", 15);
        bounds = new Bounds(0, 0, width, height);
        innerBounds = new Bounds(bounds.startX()+1, bounds.startY()+1, bounds.width(), bounds.height());
    }


    @Override
    protected void initEngine() {
        super.initEngine();

        initHealthMeter();

        initScore();

        MatchTime matchTime = new MatchTime(120);
        matchTime.getTimeEndSignal().add(this::timerEnd);
        engine.addSystem(new TimeSystem(viewport, entityBuilder, matchTime));
      }

    private void initHealthMeter() {
        HealthMeterSystem healthMeterSystem = new HealthMeterSystem(viewport, entityBuilder, app.skin);
        engine.addSystem(healthMeterSystem);
    }

    private void initScore() {
        ScoreSystem scoreSystem = new ScoreSystem();
        engine.addSystem(scoreSystem);
        engine.addSystem(new ScoreDisplaySystem(viewport, entityBuilder, scoreSystem.getScore()));
    }

    private void timerEnd(Signal<Boolean> signal, boolean object) {
        createEndText();

        removeShootingSystems();

        updateScore();

        Timeline.createSequence()
                .pushPause(3)
                .push(Tween.call((type, source) -> app.loadScreen(gameLobbyScreen)))
                .start(TweenM.getManager());

        signal.removeAllListeners();
    }

    private void createEndText() {
        entityBuilder.build("GUIText");
        TextComponent textC = entityBuilder.getComponent(TextComponent.class);
        textC.offset.set(0, 1);
        textC.label.setText("[%300]{SHRINK=2.0;1.0;false}Time is up!");
        engine.addEntity(entityBuilder.getEntity());

    }

    private void removeShootingSystems() {
        engine.removeSystem(engine.getSystem(OtherSocketShootingSystem.class));
        engine.removeSystem(engine.getSystem(SocketShootingInputSystem.class));
        engine.removeSystem(engine.getSystem(DamagingSystem.class));
    }

    private void updateScore() {
        app.getSocket("/inRoom").emit("getScore", "", (Ack) respond -> {
            Array<Score> allScore = new Array<>();

            JSONArray allScoreData = (JSONArray) respond[0];

            for(int i = 0; i < allScoreData.length(); i++) {
                try {
                    JSONObject scoreData = ((JSONObject) allScoreData.get(i));

                    String playerId = scoreData.getString("id");
                    String playerName = scoreData.getString("name");
                    int scoreValue = scoreData.getInt("score");

                    allScore.add(new Score(playerId, playerName, scoreValue));
                } catch (JSONException e) {
                    Gdx.app.log("ScoreError", "Failed reading score");
                }
            }

            gameLobbyScreen.updateScore(allScore);

            if(app.getSocket("/host").connected())
                app.getSocket("/host").emit("endGame");
        });
    }


    @Override
    protected Viewport initViewport() {
        return new ExtendViewport(13.5f,8, new BoundedCamera(bounds));
    }

    private void initSocket() {
        Socket room = app.getSocket("/inRoom");

        engine.includeServerInteractions(room, this::createTank);
        engine.addSystem(new SocketScoreSystem(room));
        engine.addSystem(new SocketContactScoreSystem(room));
    }

    @Override
    public void show() {
        app.getSocket("/inRoom").emit("ready");
        createGrid();
    }

    private void createGrid() {

        createEmptyBox();

        createFilling();

        createDecor();
    }

    private void createEmptyBox() {
        new BorderPlacer(bounds).iterate(spot -> entityCreator.createEntityOn(spot.x, spot.y, "border"));

        entityCreator.setSummonerType(SummonerType.Sprite);
        new SquarePlacer(bounds).iterate(spot -> entityCreator.createEntityOn(spot.x, spot.y, 2, "plain"));
    }

    private void createFilling() {
        LimitedPlacer randomPlacer = new IntPlacer(bounds, new UniqueRandomPlacer(innerBounds, random));

        entityCreator.clearSettings();
        randomPlacer.setMaxPlaced(convertPercentToAmountOf("box"));
        randomPlacer.iterate( spot -> placeWithRandomTransforms(spot, "box"));
        randomPlacer.setMaxPlaced(convertPercentToAmountOf("gasoline"));
        randomPlacer.iterate( spot -> placeWithRandomTransforms(spot, "gasoline"));
        randomPlacer.setMaxPlaced(convertPercentToAmountOf("bush"));
        randomPlacer.iterate( spot -> placeWithRandomTransforms(spot, "bush"));
    }

    private int convertPercentToAmountOf(String fillingName) {
        return (int) ((bounds.width()-1) * (bounds.height()-1)  * gameSettings.fillingPercentages().get(fillingName, 0)/100f);
    }

    private void placeWithRandomTransforms(Vector2 spot, String name) {
        SummonsComponent settings = entityCreator.getSummoningSettings();
        settings.scaleX = settings.scaleY = random.nextFloat(.5f, 1.2f);
        settings.degAngle = random.nextInt(16) * 22.5f;
        entityCreator.createEntityOn(spot.x, spot.y, name);
    }

    private void createDecor() {
        LimitedPlacer decorPlacer = new UniqueRandomPlacer(bounds, random);
        decorPlacer.setMaxPlaced((int)((bounds.width()-bounds.startX()) * (bounds.height()-bounds.startY()) / 4));
        entityCreator.setSummonerType(SummonerType.Sprite);
        SummonsComponent settings = entityCreator.getSummoningSettings();
        settings.scaleX = .25f;
        settings.scaleY = .25f;

        decorPlacer.iterate(spot -> {
            settings.degAngle = random.nextInt(16) * 22.5f;
            entityCreator.createEntityOn(spot.x, spot.y, 1.5f, "decor");
        });
    }
    private void createTank(String id) {
        entityCreator.clearSettings();
        createTankTemplate(id);
        entityCreator.getSummoningSettings();
        LimitedPlacer repeatRandomPlacer = new RepeatRandomPlacer(innerBounds, random);

        repeatRandomPlacer.setMaxPlaced(1);
        entityCreator.setSummonerType(SummonerType.Tank);
        repeatRandomPlacer.iterate(spot -> entityCreator.createEntityOn(spot.x, spot.y, id));
        entityCreator.setSummonerType(SummonerType.Default);
        repeatRandomPlacer.iterate(spot -> entityCreator.createEntityOn(spot.x, spot.y, 1, "spawn"));
    }

    private void createTankTemplate(String id) {
        try {
            app.componentInitializer.initField(entityCreator.getCreationSettings(TankTemplateComponent.class), "tankConfig", id.split(" ")[0]);
        } catch (NoSuchFieldException e) {
            Gdx.app.log("Error", "Failed adding tankTemplate for " + id);
        }
    }
}
