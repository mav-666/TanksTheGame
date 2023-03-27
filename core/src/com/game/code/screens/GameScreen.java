package com.game.code.screens;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.game.code.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.AssetManagment.ParticlePoolApplier;
import com.game.code.BattleField.*;
import com.game.code.Tank.CabData;
import com.game.code.Tank.Head.HeadData;
import com.game.code.Tank.Tank;
import com.game.code.Tank.TankData;

import java.util.HashMap;


//одина единица в мире равна танку (считаем за квадрат)
public class GameScreen implements Screen {
    private final Stage stage, stageUI;
    private final AssetRequestProcessor assetRequestProcessor;

//    private final TmxMapLoader tmxMapLoader;

    final static float MAP_WIDTH = 50f, MAP_HEIGHT = 50f;

    private final BoundedCamera camera;

    private final World world;
    private final BodyDisposal bodyDisposal;

//    private final Box2DDebugRenderer box2DDebugRenderer;

    public GameScreen(Application application) {

        camera = new BoundedCamera(MAP_WIDTH, MAP_HEIGHT);

        stage = new Stage(new FillViewport(9, 6, camera), application.batch);
        stageUI = new Stage(new FillViewport(18, 12, camera), application.batch);

        Gdx.input.setInputProcessor(new InputMultiplexer(stage, stageUI));

        world = new World(new Vector2(0,0), false);
        DisposeAfterContact disposeAfterContact = new DisposeAfterContact(new ShareInfoContactListener(), world);
        bodyDisposal = disposeAfterContact;
        world.setContactListener(disposeAfterContact);
        //debug
//        box2DDebugRenderer = new Box2DDebugRenderer();
        //stage.setDebugAll(true);
        //stage.setDebugInvisible(true);
        //debug

        AssetManager assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader());

        assetRequestProcessor  = new AssetRequestProcessor(assetManager);
        assetRequestProcessor.addAssetStrategy(new ParticlePoolApplier(new HashMap<>()));

        JsonValue jsonValue = new JsonReader().parse(Gdx.files.internal("Json/Tanks.json")).get("default");
        Json json = new Json();

        TankData tankData = new TankData();

        try {
            tankData.headData = json.fromJson(Class.forName(jsonValue.get("head").getString("class")).asSubclass(HeadData.class), jsonValue.get("head").toJson(JsonWriter.OutputType.json));
            tankData.cabData = json.fromJson(CabData.class, jsonValue.get("cab").toJson(JsonWriter.OutputType.json));
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        HashMap<String, TankData> tankDataHashMap = new HashMap<>();

        tankDataHashMap.put("1", tankData);
        tankDataHashMap.put("2", tankData);
        tankDataHashMap.put("3", tankData);
        tankDataHashMap.put("4", tankData);

        TankBuilder tankBuilder = new TankBuilder(tankDataHashMap,
                new BoxBuilder( 0.20f,
                        new GrassBuilder( 0.005f,
                                new BorderBuilder(
                                        new PlainBuilder(190, world, MAP_WIDTH, MAP_HEIGHT, 1, 1, "sand")
                                ))));


        tankBuilder.buildBattleField();

        ((AssetRequest) tankBuilder).request(assetRequestProcessor);

        Tank playerTank = (Tank) tankBuilder.getTanks().getChildren().get(tankBuilder.getRandom().nextInt(tankBuilder.getTanks().getChildren().size));

        camera.attach(playerTank);
        playerTank.remove();
        tankBuilder.getTanks().addActor(new Player(playerTank));



        stage.addActor(((BattleFieldBuilder) tankBuilder).createBattleField());



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


//        System.out.println(Gdx.graphics.getFramesPerSecond());

//        box2DDebugRenderer.render(world, camera.combined);

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
