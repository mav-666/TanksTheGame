package com.game.code.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.code.Application;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.Summoners.BattlefieldSettingSummoner;
import com.game.code.EntityBuilding.Summoners.SummonerType;
import com.game.code.EntityBuilding.battlefiled.*;
import com.game.code.components.BattlefieldSettingTemplateComponent;
import com.game.code.components.ButtonTemplateComponent;
import com.game.code.components.SummonsComponent;
import com.game.code.components.TankTemplateComponent;
import com.game.code.utils.BoundedCamera;
import com.game.code.utils.Bounds;
import io.socket.client.Socket;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;

public class GameLobbyScreen extends EngineScreen {

    private final Bounds bounds = new Bounds(12, 12);
    private final BattlefieldSettings battlefieldSettings = new BattlefieldSettings();
    private final float SPAWN_X = 4.5f, SPAWN_Y = 3;

    protected String roomId;

    public GameLobbyScreen(Application app) {
        super(app);
    }

    @Override
    public void loaded() {
        super.loaded();
        createGrid();
        initSocket();
    }

    @Override
    protected void initEngine(Viewport viewport) {
        super.initEngine(viewport);
        entitySummonerProvider.add(new BattlefieldSettingSummoner(entityBuilder, engine, app.skin, entityCreator));
    }

    @Override
    protected Viewport initViewport() {
        return new ExtendViewport(9, 6, new BoundedCamera(bounds));
    }

    protected void initSocket() {
        Socket socket = app.getSocket("/inRoom");

        socket.on("gameClosed", args -> app.loadScreen(new MenuScreen(app)));
        socket.on("gameStarted", args -> app.loadScreen(new GameScreen(app, roomId)));


        socket.on("logPlayer", this::logPlayerEvent);

        engine.includeServerInteractions(socket, this::createTank);
    }

    protected void logPlayerEvent(Object... args) {
        String playerName = Gdx.app.getPreferences("Prefs").getString("playerName");
        app.getSocket("/inRoom").emit("playerLogged", new JSONObject(Map.of("name", playerName)));
    }


    private void createGrid() {
        new BorderPlacer(bounds).iterate(spot -> entityCreator.createEntityOn(spot.x, spot.y, "Border"));

        entityCreator.createEntityOn(SPAWN_X,SPAWN_Y, 1, "spawn");

        entityCreator.setSummonerType(SummonerType.Sprite);
        new SquarePlacer(bounds).iterate(spot -> entityCreator.createEntityOn(spot.x, spot.y, 2, "plain"));

        entityCreator.setSummonerType(SummonerType.Button);

        entityCreator.getCreationSettings(ButtonTemplateComponent.class).activateEvent = () ->  {
            applyGameSettings();
            app.getSocket("/host").emit("startGame");
        };
        entityCreator.createEntityOn(11, 6, "play");

        entityCreator.getCreationSettings(ButtonTemplateComponent.class).activateEvent =
                () -> app.loadScreen(new MenuScreen(app));
        entityCreator.createEntityOn( 9, 2, -1, "back");

        entityCreator.clearSettings();
        entityCreator.setSummonerType(SummonerType.BattlefieldSetting);
        entityCreator.getCreationSettings(BattlefieldSettingTemplateComponent.class).battlefieldSettings = battlefieldSettings;
        entityCreator.createEntityOn(3,6, "box");
        entityCreator.createEntityOn(5f,6, "bush");
        entityCreator.createEntityOn(7,6, "gasoline");

        createDecor();
    }

    private void applyGameSettings() {
        battlefieldSettings.levelAllValues();
        app.getSocket("/host").emit("applyGameSettings", new JSONObject(Map.of(
                "bush", battlefieldSettings.getPercentageOf("bush"),
                "gasoline", battlefieldSettings.getPercentageOf("gasoline"),
                "box", battlefieldSettings.getPercentageOf("box"),
                "width", 10,
                "height", 10
        )));
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

    private void createTank(String id) {
        entityCreator.clearSettings();
        createTankTemplate(id);
        entityCreator.setSummonerType(SummonerType.Tank);
        entityCreator.createEntityOn(SPAWN_X, SPAWN_Y, id);
    }

    private void createTankTemplate(String id) {
        try {
            ComponentInitializer.getInstance().initField(entityCreator.getCreationSettings(TankTemplateComponent.class), "tankConfig", id.split(" ")[0]);
        } catch (NoSuchFieldException e) {
            Gdx.app.log("Error", "Failed adding tankTemplate for " + id);
        }
    }
}
