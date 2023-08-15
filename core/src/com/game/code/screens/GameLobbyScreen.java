package com.game.code.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.code.Application;
import com.game.code.EntityBuilding.Summoners.SummonerType;
import com.game.code.EntityBuilding.battlefiled.*;
import com.game.code.Socket.OtherSocketAimingSystem;
import com.game.code.Socket.OtherSocketMovementSystem;
import com.game.code.Socket.OtherSocketShootingSystem;
import com.game.code.Socket.SocketPlayerCreationSystem;
import com.game.code.UI.Meter;
import com.game.code.components.*;
import com.game.code.screens.Loading.TaskLoader;
import com.game.code.systems.DamagingSystem;
import com.game.code.utils.*;
import com.github.tommyettinger.textra.TypingLabel;
import io.socket.client.Socket;
import org.json.JSONObject;

import java.util.*;

public abstract class GameLobbyScreen extends EngineScreen {
    private final static float SPAWN_X = 2, SPAWN_Y = 3;

    private final Bounds bounds = new Bounds(16, 10);
    protected final GameSettings gameSettings = new GameSettings();
    private final PercentLeveler percentLeveler = new PercentLeveler();

    private Scoreboard scoreboard;

    protected String roomId;

    public GameLobbyScreen(Application app) {
        super(app);
    }

    @Override
    public TaskLoader getLoadingTask() {
        return TaskLoader.create()
                .add(super.getLoadingTask())
                .add(this::initSocket, "socket")
                .get();
    }

    @Override
    protected void initEngine() {
        super.initEngine();
        engine.removeSystem(engine.getSystem(DamagingSystem.class));
    }

    @Override
    protected Viewport initViewport() {
        return new ExtendViewport(9, 6, new BoundedCamera(bounds));
    }

    protected void initSocket() {
        Socket socket = app.getSocket("/inRoom");

        socket.on("gameClosed", args -> backButtonEvent());
        socket.on("gameStarted", args -> app.loadScreen(new GameScreen(app, roomId, this)));
        socket.on("logPlayer", this::logPlayerEvent);


        engine.includeServerInteractions(socket, this::createTank);
        engine.removeSystem(engine.getSystem(OtherSocketShootingSystem.class));
    }

    private void createTank(String id) {
        entityCreator.clearSettings();
        createTankTemplate(id);
        entityCreator.setSummonerType(SummonerType.Tank);
        entityCreator.createEntityOn(SPAWN_X, SPAWN_Y, id);
    }

    private void createTankTemplate(String id) {
        try {
            app.componentInitializer.initField(entityCreator.getCreationSettings(TankTemplateComponent.class), "tankConfig", id.split(" ")[0]);
        } catch (NoSuchFieldException e) {
            Gdx.app.log("Error", "Failed adding tankTemplate for " + id);
        }
    }

    private void logPlayerEvent(Object... args) {
        String playerName = Gdx.app.getPreferences("Prefs").getString("playerName");
        app.getSocket("/inRoom").emit("playerLogged", new JSONObject(Map.of("name", playerName)));
    }

    protected void createGrid() {
        createEmptyBox();

        createNavigationButtons();

        createGameSettings();

        createScoreBoard();

        createDecor();
    }

    private void createEmptyBox() {
        new BorderPlacer(bounds).iterate(spot -> entityCreator.createEntityOn(spot.x, spot.y, "Border"));

        entityCreator.createEntityOn(SPAWN_X,SPAWN_Y, 1, "spawn");

        entityCreator.setSummonerType(SummonerType.Sprite);
        new SquarePlacer(bounds).iterate(spot -> entityCreator.createEntityOn(spot.x, spot.y, 2, "plain"));
    }

    private void createNavigationButtons() {
        entityCreator.clearSettings();
        entityCreator.setSummonerType(SummonerType.Button);

        entityCreator.getCreationSettings(ButtonTemplateComponent.class).activateEvent = () ->  {
            applyGameSettings();
            Socket host = app.getSocket("/host");
            if(host.connected())
                host.emit("startGame");
        };
        entityCreator.createEntityOn(12, 6, "play");

        entityCreator.getCreationSettings(ButtonTemplateComponent.class).activateEvent = this::backButtonEvent;
        entityCreator.createEntityOn( 9, 2, -1, "back");
    }

    protected void backButtonEvent() {
        Socket socket = app.getSocket("/inRoom");
        socket.disconnect();
        socket.off();

        app.loadScreen(new MenuScreen(app));
    }

    private void createGameSettings() {
        entityCreator.clearSettings();

        initSettingSummoner();

        entityCreator.setSummonerType(SummonerType.BattlefieldSetting);
        SettingTemplateComponent meterTemplateC = entityCreator.getCreationSettings(SettingTemplateComponent.class);

        createFillingSettings(meterTemplateC);

        createOtherSettings(meterTemplateC);

        createSettingsText();
    }

    protected abstract void initSettingSummoner();

    private void createFillingSettings(SettingTemplateComponent meterTemplateC) {
        meterTemplateC.setting = gameSettings.fillingPercentages();
        meterTemplateC.meterConfig = new Meter.MeterConfig(0, 100, 5);

        entityCreator.createEntityOn(2,6, "box");
        entityCreator.createEntityOn(4f,6, "bush");
        entityCreator.createEntityOn(6,6, "gasoline");
    }

    private void createOtherSettings(SettingTemplateComponent meterTemplateC) {
        meterTemplateC.labelText = "[%50]{value}";
        meterTemplateC.setting = gameSettings.otherSettings();
        meterTemplateC.meterConfig = new Meter.MeterConfig(15, 100, 5);

        entityCreator.createEntityOn(8, 6, "width");
        entityCreator.createEntityOn(10, 6, "height");
    }

    private void createSettingsText() {
        entityBuilder.build("Text");
        TypingLabel label = entityBuilder.getComponent(TextComponent.class).label;
        label.setText("Game settings");
        label.setColor(app.skin.getColor("brown"));

        entityBuilder.getComponent(TransformComponent.class).position.set(4, 9);
        engine.addEntity(entityBuilder.getEntity());
    }

    private void applyGameSettings() {
        percentLeveler.levelAllValues(gameSettings.fillingPercentages());

        app.getSocket("/host").emit("applyGameSettings", new JSONObject(Map.of(
                "bush", gameSettings.fillingPercentages().get("bush", 0),
                "gasoline", gameSettings.fillingPercentages().get("gasoline", 0),
                "box", gameSettings.fillingPercentages().get("box", 0),
                "width", gameSettings.otherSettings().get("width", 15),
                "height", gameSettings.otherSettings().get("height", 15)
        )));
    }

    private void createScoreBoard() {
        entityBuilder.build("Text");
        entityBuilder.getComponent(TransformComponent.class).position.set(7, 4);
        TypingLabel label = entityBuilder.getComponent(TextComponent.class).label;
        scoreboard = new Scoreboard(label, app.skin.getColor("brown"));
        engine.addEntity(entityBuilder.getEntity());
    }

    private void createDecor() {
        Random random = new Random();
        LimitedPlacer decorPlacer = new UniqueRandomPlacer(bounds, random);
        decorPlacer.setMaxPlaced((int)((bounds.width()-bounds.startX()) * (bounds.height()-bounds.startY()) / 20));

        decorPlacer.setMaxPlaced(100);
        entityCreator.setSummonerType(SummonerType.Sprite);
        SummonsComponent settings = entityCreator.getSummoningSettings();
        settings.scaleX = 0.25f;
        settings.scaleY = 0.25f;

        decorPlacer.iterate(spot -> {
            settings.degAngle = random.nextInt(16) * 22.5f;
            entityCreator.createEntityOn(spot.x, spot.y, 1.5f, "decor");
        });
    }

    public void updateScore(Array<Score> allScore) {
        scoreboard.clear();
        allScore.forEach((score) -> scoreboard.addScore(score));
        scoreboard.sort();
        scoreboard.prettyPrint();
    }

    @Override
    public void show() {
        super.show();
        engine.getSystem(SocketPlayerCreationSystem.class).setProcessing(true);
        engine.getSystem(OtherSocketMovementSystem.class).setProcessing(true);
        engine.getSystem(OtherSocketAimingSystem.class).setProcessing(true);
    }

    @Override
    public void hide() {
        engine.getSystem(SocketPlayerCreationSystem.class).setProcessing(false);
        engine.getSystem(OtherSocketMovementSystem.class).setProcessing(false);
        engine.getSystem(OtherSocketAimingSystem.class).setProcessing(false);
    }
}
