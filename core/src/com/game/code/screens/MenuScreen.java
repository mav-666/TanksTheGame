package com.game.code.screens;

import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.code.Application;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.FieldInitializers.EventFieldInitializer;
import com.game.code.EntityBuilding.Summoners.SummonerType;
import com.game.code.EntityBuilding.Summoners.EventProvider;
import com.game.code.EntityBuilding.battlefiled.BorderPlacer;
import com.game.code.EntityBuilding.battlefiled.SquarePlacer;
import com.game.code.components.SummonsComponent;
import com.game.code.systems.HealthMeterSystem;
import com.game.code.utils.BoundedCamera;
import com.game.code.utils.Bounds;

public class MenuScreen extends EngineScreen {

    private final Bounds bounds = new Bounds(15, 10);

    public MenuScreen(Application app) {
        super(app);

        Viewport viewport = new ExtendViewport(9,6, new BoundedCamera(bounds));

        initEngine(viewport);
        initEvents();
    }

    private void initEvents() {
        EventProvider eventProvider = new EventProvider();
        eventProvider.add("play", () -> app.loadScreen(new GameScreen(app)));

        ComponentInitializer.getInstance().addInitializer(new EventFieldInitializer(eventProvider));
    }

    @Override
    protected void initEngine(Viewport viewport) {
        super.initEngine(viewport);
        engine.includeDebug(entitySummonerProvider.getEntityBuilder(), world, viewport);

        initHealthMeter();
    }

    private void initHealthMeter() {
        HealthMeterSystem healthMeterSystem = new HealthMeterSystem(viewport, entitySummonerProvider.getEntityBuilder(), app.skin);
        engine.addSystem(healthMeterSystem);
        engine.addEntityListener(HealthMeterSystem.FAMILY, healthMeterSystem);
    }

    @Override
    public void loaded() {
        super.loaded();

        createGrid();createKeyboard();
    }

    private void createGrid() {
        new BorderPlacer(bounds).iterate(spot -> entityCreator.createEntityOn(spot.x, spot.y, "Border"));

        entityCreator.setSummonerType(SummonerType.Sprite);
        new SquarePlacer(bounds).iterate(spot -> entityCreator.createEntityOn(spot.x, spot.y, 2, "plain"));
        SummonsComponent settings = entityCreator.getCreationSettings();
        settings.scaleX = settings.scaleY = 2;
        entityCreator.createEntityOn(11, 2, 1,"ShootKey");
        entityCreator.createEntityOn(11, 5, 1,"MoveKeys");
        entityCreator.clearSettings();
        entityCreator.createEntityOn(11, 5, "Box");
        entityCreator.createEntityOn(10, 5, "Box");
        entityCreator.createEntityOn(11, 4, "Gasoline");
        entityCreator.createEntityOn(7,6, "Bush");
        entityCreator.setSummonerType(SummonerType.Button);
        entityCreator.createEntityOn(10, 4, "play");

        createTank();
    }

    private void createTank() {
        entityCreator.clearSettings();
        entityCreator.setSummonerType(SummonerType.Tank);
        entityCreator.createEntityOn(9, 2, "player");
        entityCreator.setSummonerType(SummonerType.Sprite);
        entityCreator.createEntityOn(9, 2, 1, "spawn");
    }

    private void createKeyboard() {
        entityCreator.setSummonerType(SummonerType.Keyboard);
        entityCreator.createEntityOn(3,3, "keyboard");
    }
}
