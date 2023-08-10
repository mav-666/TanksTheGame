package com.game.code.screens;

import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.code.Application;
import com.game.code.EntityBuilding.Summoners.KeyboardSummoner;
import com.game.code.EntityBuilding.Summoners.SummonerType;
import com.game.code.EntityBuilding.battlefiled.BorderPlacer;
import com.game.code.EntityBuilding.battlefiled.SquarePlacer;
import com.game.code.components.ButtonTemplateComponent;
import com.game.code.components.SummonsComponent;
import com.game.code.utils.BoundedCamera;
import com.game.code.utils.Bounds;

public class MenuScreen extends EngineScreen {

    private final Bounds bounds = new Bounds(12, 12);

    public MenuScreen(Application app) {
        super(app);
    }

    @Override
    protected void initEngine(Viewport viewport) {
        super.initEngine(viewport);
        entitySummonerProvider.add(new KeyboardSummoner(entityBuilder, engine, app.skin));
        engine.includeDebug(entityBuilder, world, viewport);
    }

    @Override
    public void loaded() {
        super.loaded();
        createGrid();
        createKeyboard();
    }

    @Override
    protected Viewport initViewport() {
        return new ExtendViewport(9, 6, new BoundedCamera(bounds));
    }

    private void createGrid() {
        new BorderPlacer(bounds).iterate(spot -> entityCreator.createEntityOn(spot.x, spot.y, "Border"));

        entityCreator.setSummonerType(SummonerType.Sprite);
        new SquarePlacer(bounds).iterate(spot -> entityCreator.createEntityOn(spot.x, spot.y, 2, "plain"));

        SummonsComponent settings = entityCreator.getSummoningSettings();
        settings.scaleX = settings.scaleY = 2;
        entityCreator.getSummoningSettings().degAngle = -5;
        entityCreator.createEntityOn(6, 2, 1,"ShootKey");
        entityCreator.getSummoningSettings().degAngle = 10;
        entityCreator.createEntityOn(6, 5, 1,"MoveKeys");

        entityCreator.clearSettings();
        entityCreator.createEntityOn(11, 1, "Box");
        entityCreator.createEntityOn(11, 2, "Box");

        entityCreator.getSummoningSettings().degAngle = -2;
        entityCreator.createEntityOn(10, 2, "Box");
        entityCreator.createEntityOn(9, 2, "Box");
        entityCreator.getSummoningSettings().degAngle = 20;
        entityCreator.createEntityOn(10, 2, "Gasoline");

        entityCreator.createEntityOn(6.5f, 5, "Box");
        entityCreator.createEntityOn(5.5f, 5, "Box");
        entityCreator.createEntityOn(6, 4, "Gasoline");

        entityCreator.clearSettings();
        entityCreator.createEntityOn(11,6, "Bush");
        entityCreator.createEntityOn(11,5, "Bush");
        entityCreator.createEntityOn(10,6, "Bush");
        entityCreator.createEntityOn(11,11, "Bush");
        entityCreator.createEntityOn(10,11, "Bush");
        entityCreator.createEntityOn(2,11, "Bush");


        entityCreator.getSummoningSettings().degAngle = 30;
        entityCreator.createEntityOn(9, 8.5f, "Box");
        entityCreator.getSummoningSettings().degAngle = 0;
        entityCreator.createEntityOn(11, 8, "Box");
        entityCreator.setSummonerType(SummonerType.Button);

        entityCreator.getCreationSettings(ButtonTemplateComponent.class).activateEvent =  () -> app.loadScreen(new HostScreen(app));
        entityCreator.createEntityOn(10, 8, "host");

        entityCreator.getCreationSettings(ButtonTemplateComponent.class).activateEvent =  () -> app.loadScreen(new OnlineGameListScreen(app));
        entityCreator.createEntityOn(8, 9f, "join");

        createTank();
    }

    private void createTank() {
        entityCreator.clearSettings();
        entityCreator.setSummonerType(SummonerType.Tank);
        entityCreator.createEntityOn(4, 2, "player");
        entityCreator.setSummonerType(SummonerType.Default);
        entityCreator.createEntityOn(4, 2, 1, "spawn");
    }

    private void createKeyboard() {
        entityCreator.setSummonerType(SummonerType.Keyboard);
        entityCreator.createEntityOn(3.5f,8, "keyboard");
    }
}
