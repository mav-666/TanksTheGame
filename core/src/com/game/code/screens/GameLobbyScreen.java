package com.game.code.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectFloatMap;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.code.Application;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.Summoners.BattlefieldSettingSummoner;
import com.game.code.EntityBuilding.Summoners.SummonerType;
import com.game.code.EntityBuilding.battlefiled.*;
import com.game.code.UI.Meter;
import com.game.code.components.*;
import com.game.code.screens.Loading.TaskLoader;
import com.game.code.systems.DamagingSystem;
import com.game.code.utils.BoundedCamera;
import com.game.code.utils.Bounds;
import com.github.tommyettinger.textra.TypingLabel;
import io.socket.client.Socket;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;

public abstract class GameLobbyScreen extends EngineScreen {

    private final Bounds bounds = new Bounds(16, 10);
    private final GameSettings gameSettings = new GameSettings();
    private final float SPAWN_X = 2, SPAWN_Y = 3;

    protected String roomId;

    public GameLobbyScreen(Application app) {
        super(app);
    }

    @Override
    public TaskLoader getLoadingTask() {
        return TaskLoader.create()
                .add(super.getLoadingTask())
                .add(this::createGrid, "grid")
                .get();
    }

    @Override
    protected void initEngine() {
        super.initEngine();
        engine.removeSystem(engine.getSystem(DamagingSystem.class));
        entitySummonerProvider.add(new BattlefieldSettingSummoner(entityBuilder, engine, app.skin, entityCreator));
    }

    @Override
    protected Viewport initViewport() {
        return new ExtendViewport(9, 6, new BoundedCamera(bounds));
    }

    private void createGrid() {
        createEmptyBox();

        createNavigationButtons();

        createGameSettings();

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
            app.getSocket("/host").emit("startGame");
        };
        entityCreator.createEntityOn(4, 4, "play");

        entityCreator.getCreationSettings(ButtonTemplateComponent.class).activateEvent = this::backButtonEvent;
        entityCreator.createEntityOn( 9, 2, -1, "back");
    }

    private void createGameSettings() {
        entityCreator.clearSettings();

        entityCreator.setSummonerType(SummonerType.BattlefieldSetting);
        MeterTemplateComponent meterTemplateC = entityCreator.getCreationSettings(MeterTemplateComponent.class);

        createFillingSettings(meterTemplateC);

        createOtherSettings(meterTemplateC);

        createSettingsText();
    }

    private void createFillingSettings(MeterTemplateComponent meterTemplateC) {
        meterTemplateC.setting = gameSettings.fillingPercentages();
        meterTemplateC.meterConfig = new Meter.MeterConfig(0, 100, 5);

        entityCreator.createEntityOn(2,6, "box");
        entityCreator.createEntityOn(4f,6, "bush");
        entityCreator.createEntityOn(6,6, "gasoline");
    }

    private void createOtherSettings(MeterTemplateComponent meterTemplateC) {
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
        new PercentLeveler().levelAllValues(gameSettings.fillingPercentages());

        app.getSocket("/host").emit("applyGameSettings", new JSONObject(Map.of(
                "bush", gameSettings.fillingPercentages().get("bush", 0),
                "gasoline", gameSettings.fillingPercentages().get("gasoline", 0),
                "box", gameSettings.fillingPercentages().get("box", 0),
                "width", gameSettings.otherSettings().get("width", 10),
                "height", gameSettings.otherSettings().get("height", 10)
        )));
    }

    protected void backButtonEvent() {
        app.loadScreen(new MenuScreen(app));
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

    @Override
    public void show() {
        initSocket();
    }

    protected void initSocket() {
        Socket socket = app.getSocket("/inRoom");

        socket.on("gameClosed", args -> {
            socket.disconnect();
            socket.off();
            app.getScreen(MenuScreen.class).ifPresentOrElse(app::loadScreen, () -> app.loadScreen(new MenuScreen(app)));
        });
        socket.on("gameStarted", args -> app.loadScreen(new GameScreen(app, roomId)));


        socket.on("logPlayer", this::logPlayerEvent);

        engine.includeServerInteractions(socket, this::createTank);
    }

    protected void logPlayerEvent(Object... args) {
        String playerName = Gdx.app.getPreferences("Prefs").getString("playerName");
        app.getSocket("/inRoom").emit("playerLogged", new JSONObject(Map.of("name", playerName)));
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

    static class PercentLeveler {

        private ObjectFloatMap<String> percents;

        public void levelAllValues(ObjectFloatMap<String> percents) {
            this.percents = percents;
            float sum = calculateSumPercent();
            if(sum <= 100) return;

            float balanceModifier = calculateBalanceModifier(sum);
            percents.keys().forEach((key) -> transformParameter(key, balanceModifier));
        }

        private float calculateSumPercent() {
            float sum = 0;
            for(float percentage :  percents.values().toArray().toArray())
                sum += percentage;

            return sum;
        }

        private float calculateBalanceModifier(float sumPercent) {
            return sumPercent/100;
        }

        private void transformParameter(String fillingName, float modifier) {
            percents.put(fillingName, percents.get(fillingName, 0) / modifier);
        }
    }
}
