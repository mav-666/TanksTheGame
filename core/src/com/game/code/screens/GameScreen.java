package com.game.code.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.code.Application;
import com.game.code.EntityBuilding.Summoners.SummonerType;
import com.game.code.EntityBuilding.battlefiled.*;
import com.game.code.utils.BoundedCamera;
import com.game.code.utils.Bounds;

import java.util.Random;


public class GameScreen extends EngineScreen {

    private final Random random;
    private final Bounds bounds = new Bounds(30, 30);
    Bounds innerBounds = new Bounds(bounds.startX()+1, bounds.startY()+1, bounds.width()-1, bounds.height()-1);

    public GameScreen(Application app) {
        super(app);
        random = new Random(app.seed);

        Viewport viewport = new FillViewport(13.5f,8, new BoundedCamera(bounds));

        initEngine(viewport);
        engine.includeDebug(entitySummonerProvider.getEntityBuilder(), world, viewport);
    }

    @Override
    public void loaded() {
        super.loaded();
        initSocket();
        createGrid();
    }

    private void initSocket() {
        engine.includeServerInteractions(app.getSocket(), this::createTank);
        app.getSocket().connect();
    }

    private void createGrid() {
        LimitedPlacer randomPlacer = new UniqueRandomPlacer(innerBounds, random);

        new BorderPlacer(bounds).iterate(spot -> entityCreator.createEntityOn(spot.x, spot.y, "Border"));

        entityCreator.setSummonerType(SummonerType.Sprite);
        new SquarePlacer(bounds).iterate(spot -> entityCreator.createEntityOn(spot.x, spot.y, 2, "plain"));
        entityCreator.setSummonerType(SummonerType.Default);
        randomPlacer.setMaxPlaced(20);
        randomPlacer.iterate( spot -> entityCreator.createEntityOn(spot.x, spot.y, "Box"));
        randomPlacer.iterate( spot -> entityCreator.createEntityOn(spot.x, spot.y, "Gasoline"));
        randomPlacer.iterate( spot -> entityCreator.createEntityOn(spot.x, spot.y, "Bush"));
    }

    private void createTank(String id) {
        LimitedPlacer repeatRandomPlacer = new RepeatRandomPlacer(innerBounds, random);

        repeatRandomPlacer.setMaxPlaced(1);
        entityCreator.setSummonerType(SummonerType.Tank);
        repeatRandomPlacer.iterate(spot -> entityCreator.createEntityOn(spot.x, spot.y, id));
        entityCreator.setSummonerType(SummonerType.Sprite);
        repeatRandomPlacer.iterate(spot -> entityCreator.createEntityOn(spot.x, spot.y, 1, "spawn"));
    }

    private void createGUI() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(Gdx.input.isKeyJustPressed(Input.Keys.V))
            app.setScreen(new MenuScreen(app));
    }
}
