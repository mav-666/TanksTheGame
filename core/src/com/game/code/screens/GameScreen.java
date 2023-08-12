package com.game.code.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.code.Application;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.Summoners.SummonerType;
import com.game.code.EntityBuilding.battlefiled.*;
import com.game.code.Socket.SocketContactScoreSystem;
import com.game.code.Socket.SocketScoreSystem;
import com.game.code.components.SummonsComponent;
import com.game.code.components.TankTemplateComponent;
import com.game.code.screens.Loading.TaskLoader;
import com.game.code.systems.HealthMeterSystem;
import com.game.code.systems.ScoreDisplaySystem;
import com.game.code.systems.ScoreSystem;
import com.game.code.systems.TimeSystem;
import com.game.code.utils.BoundedCamera;
import com.game.code.utils.Bounds;
import com.game.code.utils.MatchTime;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;


public class GameScreen extends EngineScreen {

    private final Random random;
    private final String roomId;
    MatchTime matchTime = new MatchTime(120);

    SocketGameSettingsParser settingsParser = new SocketGameSettingsParser();

    private Bounds bounds;
    private Bounds innerBounds;
    public GameScreen(Application app, String roomId) {
        super(app);
        random = new Random(app.seed);
        this.roomId = roomId;
    }

    @Override
    public TaskLoader getLoadingTask() {
        return TaskLoader.create()
                .add(settingsParser.getLoadingTask())
                .add(super.getLoadingTask())
                .add(this::initSocket, "socket")
                .get();
    }

    @Override
    protected void initEngine() {
        super.initEngine();

        initHealthMeter();

        initScore();

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
        engine.includeDebug(entityBuilder, world, viewport);
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
        return (int) ((bounds.width()-1) * (bounds.height()-1)  * settingsParser.getGameSettings().fillingPercentages().get(fillingName, 0)/100f);
    }

    private void placeWithRandomTransforms(Vector2 spot, String name) {
        SummonsComponent settings = entityCreator.getSummoningSettings();
        settings.scaleX = settings.scaleY = random.nextFloat(0.5f, 1.2f);
        settings.degAngle = random.nextInt(16) * 22.5f;
        entityCreator.createEntityOn(spot.x, spot.y, name);
    }

    private void createDecor() {
        LimitedPlacer decorPlacer = new UniqueRandomPlacer(bounds, random);
        decorPlacer.setMaxPlaced((int)((bounds.width()-bounds.startX()) * (bounds.height()-bounds.startY()) / 4));
        entityCreator.setSummonerType(SummonerType.Sprite);
        SummonsComponent settings = entityCreator.getSummoningSettings();
        settings.scaleX = 0.25f;
        settings.scaleY = 0.25f;

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
            ComponentInitializer.getInstance().initField(entityCreator.getCreationSettings(TankTemplateComponent.class), "tankConfig", id.split(" ")[0]);
        } catch (NoSuchFieldException e) {
            Gdx.app.log("Error", "Failed adding tankTemplate for " + id);
        }
    }

    class SocketGameSettingsParser {

        private final GameSettings gameSettings = new GameSettings();

        private TaskLoader getLoadingTask() {
            return TaskLoader.create().loadFromSocket(app.getSocket("/inRoom"), "getGameSettings",
                    respond -> parseGameSettingsData((JSONObject) respond[0]),
                    new JSONObject(Map.of("roomId", roomId))).get();
        }

        private void parseGameSettingsData(JSONObject data) {
            setPercentageOf("bush", data);
            setPercentageOf("gasoline", data);
            setPercentageOf("box", data);

            setOtherSettings(data);
        }

        private void setPercentageOf(String fillingName, JSONObject data) {
            try{
                gameSettings.fillingPercentages().put(fillingName, (float) data.getInt(fillingName));
            } catch (JSONException e) {
                Gdx.app.log("GameSettingsError", "failed parsing " + fillingName);
            }
        }

        private void setOtherSettings(JSONObject data) {
            try{
                int width = data.getInt("width");
                int height = data.getInt("height");

                bounds = new Bounds(0, 0, width, height);
                innerBounds = new Bounds(bounds.startX()+1, bounds.startY()+1, bounds.width(), bounds.height());

            } catch (JSONException e) {
                Gdx.app.log("GameSettingsError", "failed setting size");
            }
        }

        public GameSettings getGameSettings() {
            return gameSettings;
        }
    }
}
