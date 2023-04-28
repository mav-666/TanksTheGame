package com.game.code.screens;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.viewport.FillViewport;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.game.code.BattleField.*;
import com.game.code.Entity.Breakable;
import com.game.code.Tank.PlayableTank;
import com.game.code.Tank.Tank;
import com.game.code.Tank.TankData;
import com.game.code.UI.HealthBarApplier;
import com.game.code.UI.PPM;
import com.game.code.utils.BoundedCamera;
import com.game.code.utils.Loading.AssetTaskLoader;
import com.game.code.utils.Loading.LoadingTask;
import com.game.code.utils.Loading.MultipleTaskLoader;
import com.game.code.utils.Loading.SingleTaskLoader;
import com.game.code.utils.box2d.BodyHandler;
import com.game.code.utils.box2d.ShareInfoContactListener;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URI;
import java.util.Arrays;


//одина единица в мире равна танку (считаем за квадрат)
public class GameScreen implements LoadableScreen {
    private final Application app;
    private final Stage stage, stageUI;

    final static float MAP_WIDTH = 50f, MAP_HEIGHT = 50f;

    private final BoundedCamera camera;

    private final World world;
    private final BodyHandler bodyHandler;

    Socket socket;

    private Group tanks;

    //private final Box2DDebugRenderer box2DDebugRenderer;

    protected GameScreen(Application application) {
        this.app = application;

        camera = new BoundedCamera(MAP_WIDTH, MAP_HEIGHT);

        stage = new Stage(new FillViewport(18, 12, camera), application.getBatch());
        stageUI = new Stage(new FillViewport(0,0, camera), application.getBatch());

        world = new World(new Vector2(0,0), false);
        world.setContactListener(new ShareInfoContactListener());

        bodyHandler = new BodyHandler(world);

        //debug
        //box2DDebugRenderer = new Box2DDebugRenderer();
        //stage.setDebugAll(true);
        //stage.setDebugInvisible(true);
        //debug
    }

    private void initServerConnection() {
        URI uri = URI.create("http://localhost:10578/");

        IO.Options options = IO.Options.builder().build();

        socket = IO.socket(uri, options);

        socket.on(Socket.EVENT_CONNECT, args1 -> System.out.println("Connected to server : " +  Arrays.toString(args1)));

        socket.on("bar", args1 -> {
            System.out.println((String) args1[0]);
            socket.emit("bar", "abibka");
        });

        socket.connect();
        System.out.println("hah...");
    }

    private void initBattleField() {

        Json json = new Json();
        JsonReader jsonReader = new JsonReader();
        TankData tankData = json.fromJson(TankData.class, jsonReader.parse(Gdx.files.internal("Json/Tanks.json")).get("default").toJson(JsonWriter.OutputType.json));
        BattleFieldBuilderJsonData battlefieldData = json.fromJson(BattleFieldBuilderJsonData.class, jsonReader.parse(Gdx.files.internal("Json/BattleField.json")).toJson(JsonWriter.OutputType.json));


        TankBuilderData tankBuilderData = new TankBuilderData();
        tankBuilderData.type = "tank";
        tankBuilderData.tanksData = new Array<>();

        for(int i = 0; i < 12; i++) {
            TankData tdata = tankData.clone();
            tdata.id = String.valueOf(i);
            tankBuilderData.tanksData.add(tdata);
        }

        BattleFieldBuilderFromJsonData builderFromJsonData = new BattleFieldBuilderFromJsonData();

        TankBuilder tankBuilder = (TankBuilder) builderFromJsonData.applyDecorator(
                builderFromJsonData.createBattlerFieldBuilder(bodyHandler, battlefieldData), tankBuilderData);

        tankBuilder.buildBattleField();

        tankBuilder.request(app.getAssetProcessor());

        tanks = tankBuilder.getTanks();
        Tank playerTank = (Tank) tanks.getChildren().get(tankBuilder.getRandom().nextInt(tanks.getChildren().size));

        playerTank.remove();
        PlayableTank playableTank = new PlayableTank(playerTank);
        tanks.addActor(playableTank);
        camera.attach(playerTank);
        camera.position.set(playerTank.getX(), playerTank.getY(), 0);

        stage.addActor(((BattleFieldBuilder) tankBuilder).createBattleField());
        app.getAssetProcessor().load();
    }

    private void initUIStage() {
        HealthBarApplier healthBarApplier = new HealthBarApplier();
        tanks.getChildren().forEach((tank -> healthBarApplier.applyHealthBar((Breakable) tank)));

        stageUI.addActor(healthBarApplier);

        healthBarApplier.request(app.getAssetProcessor());
        app.getAssetProcessor().load();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, stageUI));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();

        stageUI.getRoot().setScale(PPM.RATIO);
        stageUI.draw();

      //  System.out.println(Gdx.graphics.getFramesPerSecond());

        //box2DDebugRenderer.render(world, camera.combined);

        camera.update();

    }

    private void update(float delta) {
        stage.act(delta);
        stageUI.act(delta);

        bodyHandler.createDelayed();
        bodyHandler.activateBodies();

        world.step(delta, 6,2);

        bodyHandler.disposeBodies();
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
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public LoadingTask getLoadingTask() {
        return new MultipleTaskLoader(
                new SingleTaskLoader(this::initServerConnection, "server"),
                new SingleTaskLoader(this::initBattleField, "battleField"),
                new SingleTaskLoader(this::initUIStage, "ui"),
                new AssetTaskLoader(app.getAssetProcessor()));
    }

    @Override
    public LoadingScreens getLoadingScreenType() {
        return LoadingScreens.loadingBar;
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
