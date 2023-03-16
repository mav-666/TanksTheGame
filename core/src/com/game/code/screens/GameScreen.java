package com.game.code.screens;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.game.code.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.AssetManagment.ParticlePoolApplier;
import com.game.code.Tank.DefaultTank;
import com.game.code.Entity.Entity;
import com.game.code.Tank.Tank;

import java.util.ArrayList;
import java.util.HashMap;


//одина единица в мире равна танку (считаем за квадрат)
public class GameScreen implements Screen {
    private final Application app;
    private final Stage stage, stageUI;
    private final Player player;

    private final AssetRequestProcessor assetRequestProcessor;

//    private final TmxMapLoader tmxMapLoader;

    final static float MAP_WIDTH = 50f, MAP_HEIGHT = 50f;

    private final BoundedCamera camera;

    private final World world;
    private final BodyDisposal bodyDisposal;

    //private final Box2DDebugRenderer box2DDebugRenderer;

    public GameScreen(Application application) {
        app = application;

        camera = new BoundedCamera(MAP_WIDTH, MAP_HEIGHT);

        stage = new Stage(new FillViewport(18 , 12, camera), app.batch);
        stageUI = new Stage(new FillViewport(18, 12, camera), app.batch);

        Gdx.input.setInputProcessor(new InputMultiplexer(stage, stageUI));



        world = new World(new Vector2(0,0), false);
        DisposeAfterContact disposeAfterContact = new DisposeAfterContact(new ShareInfoContactListener(), world);
        bodyDisposal = disposeAfterContact;
        world.setContactListener(disposeAfterContact);
        //debug
        //box2DDebugRenderer = new Box2DDebugRenderer();
        //stage.setDebugAll(true);
        //stage.setDebugInvisible(true);
        //debug

        AssetManager assetManager = new AssetManager();

        assetRequestProcessor  = new AssetRequestProcessor(assetManager);
        assetRequestProcessor.addAssetStrategy(new ParticlePoolApplier(new HashMap<>()));

        BattleField battleField = new BattleField(world, new TextureRegion(new Texture(Gdx.files.internal("Map.png"))), MAP_WIDTH, MAP_HEIGHT);

        Tank tank = new DefaultTank(world, new Vector2(1,1), 1, 1);
        tank.setName("A");
        player = new Player(tank);

        Tank tank2 = new DefaultTank(world, new Vector2(5,5), 1, 1);
        tank2.setName("B");

        Tank tank3 = new DefaultTank(world, new Vector2(16,12), 1, 1);
        tank3.setName("C");

        assetRequestProcessor.receiveRequest(tank);
        assetRequestProcessor.receiveRequest(tank2);
        assetRequestProcessor.receiveRequest(tank3);

        camera.attach(tank);

        stage.addActor(battleField);
        stage.addActor(player);
        stage.addActor(tank);
        stage.addActor(tank2);
        stage.addActor(tank3);

        assetRequestProcessor.load();

    }

    void initUIStage() {
        Table table = new Table();
        table.setFillParent(true);

        stageUI.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if(!assetRequestProcessor.isFinished()) {
            assetRequestProcessor.update();
            return;
        }

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
        stageUI.draw();

        //box2DDebugRenderer.render(world, camera.combined);

        camera.update();

    }

    private void update(float delta) {
        stage.act(delta);
        stageUI.act(delta);

        world.step(delta, 6,2);
        bodyDisposal.destroyAllBodies();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
        stageUI.getViewport().update(width, height, false);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        assetRequestProcessor.dispose();
        stage.dispose();
        world.dispose();
    }
}
