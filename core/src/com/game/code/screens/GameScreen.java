package com.game.code.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
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
    private final Stage stage;
    private final Player player;

    private final AssetRequestProcessor assetRequestProcessor;

//    private final TmxMapLoader tmxMapLoader;

    final static float MAP_WIDTH = 50f, MAP_HEIGHT = 50f;

    private final BoundedCamera camera;

    private final World world;
    private final ArrayList<Body> destroyedBodies;

    private final Box2DDebugRenderer box2DDebugRenderer;

    public GameScreen(Application application) {
        app = application;

        camera = new BoundedCamera(MAP_WIDTH, MAP_HEIGHT);

        stage = new Stage(new FillViewport(18 , 12, camera), app.batch);
        Gdx.input.setInputProcessor(stage);

        world = initWorld();
        destroyedBodies = new ArrayList<>();
        //debug
        box2DDebugRenderer = new Box2DDebugRenderer();
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

    private World initWorld() {
        World world= new World(new Vector2(0,0), false);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Entity A = ((Entity) contact.getFixtureA().getUserData());
                Entity B = ((Entity) contact.getFixtureB().getUserData());

                A.collusionRespond(B);
                B.collusionRespond(A);
            }

            @Override
            public void endContact(Contact contact) {}
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                Entity A = ((Entity) contact.getFixtureA().getUserData());
                Entity B = ((Entity) contact.getFixtureB().getUserData());

                if(!destroyedBodies.contains(A.getBody()) && ((BodyData) A.getBody().getUserData()).flaggedForDispose)
                    destroyedBodies.add(A.getBody());
                if(!destroyedBodies.contains(B.getBody()) && ((BodyData) B.getBody().getUserData()).flaggedForDispose)
                    destroyedBodies.add(B.getBody());
            }

        });

        return world;
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

        box2DDebugRenderer.render(world, camera.combined);

        camera.update();

    }

    private void update(float delta) {
        stage.act(delta);
        world.step(delta, 6,2);

        destroyedBodies.forEach(world::destroyBody);
        destroyedBodies.clear();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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
