package com.game.code;

import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.game.code.EntityBuilding.*;
import com.game.code.EntityBuilding.FieldInitializers.Box2dBodyCreation.BodyConfig;
import com.game.code.EntityBuilding.FieldInitializers.*;
import com.game.code.EntityBuilding.FieldInitializers.BodyInitializer;
import com.game.code.EntityBuilding.Json.BodyConfigJsonFactory;
import com.game.code.EntityBuilding.Json.JsonEntityBuilder;
import com.game.code.EntityBuilding.Json.JsonLoader;
import com.game.code.EntityBuilding.Summoners.EntitySummonerProvider;
import com.game.code.EntityBuilding.battlefiled.*;
import com.game.code.FileManagment.InternalJsonLoader;
import com.game.code.Socket.*;
import com.game.code.UI.screens.AbstractScreen;
import com.game.code.UI.screens.Loading.LoadableScreen;
import com.game.code.UI.screens.Loading.TaskLoader;
import com.game.code.systems.*;
import com.game.code.utils.BoundedCamera;
import com.game.code.utils.Bounds;
import com.game.code.utils.CollusionRegister;
import com.game.code.utils.TweenUtils.ColorAccessor;
import com.game.code.utils.TweenUtils.TweenM;
import com.game.code.utils.TweenUtils.Vector2Accessor;
import io.socket.client.Socket;

import java.util.Random;

public class GameScreen extends AbstractScreen implements LoadableScreen {
    private final Engine engine = new PooledEngine();
    private final Random random;

    private final FitViewport viewport;
    private final World world = new World(Vector2.Zero, false);

    private EntitySummonerProvider entityBuilderProvider;

    EntityCreator entityCreator;

   // private final ObjectMap<String, Entity> connectedTanks;

    private final Bounds bounds = new Bounds(30, 30);

    public GameScreen(Application app) {
        super(app);

        random = new Random(1);

        initTween();

        viewport = new FitViewport(9, 6, new BoundedCamera(bounds));

        world.setContactListener(new CollusionRegister(engine));

        JsonLoader jsonLoader = new InternalJsonLoader();

        initEntityBuilder(jsonLoader);

        initEngine();
    }

    private void initSocket() {
        Socket socket = app.getClientSocket().getSocket();

        OtherSocketShootingSystem otherSocketShootingSystem = new OtherSocketShootingSystem(socket);
        engine.addEntityListener(OtherSocketShootingSystem.FAMILY, otherSocketShootingSystem);
        engine.addSystem(otherSocketShootingSystem);
        engine.addSystem(new SocketShootingInputSystem(socket));

        OtherSocketAimingSystem otherSocketAimingSystem = new OtherSocketAimingSystem(socket);
        engine.addEntityListener(OtherSocketAimingSystem.FAMILY, otherSocketAimingSystem);
        engine.addSystem(otherSocketAimingSystem);
        engine.addSystem(new SocketAimingSystem(socket));

        OtherSocketMovementSystem otherSocketMovementSystem = new OtherSocketMovementSystem(socket);
        engine.addEntityListener(OtherSocketMovementSystem.FAMILY, otherSocketMovementSystem);
        engine.addSystem(otherSocketMovementSystem);
        engine.addSystem(new SocketMovementSystem(socket));

        engine.addSystem(new SocketPlayerCreationSystem(socket, this::createTank));

        socket.connect();
    }

    private void initTween() {
        Tween.registerAccessor(Color.class, new ColorAccessor());
        Tween.registerAccessor(Vector2.class, new Vector2Accessor());
    }

    private void initEntityBuilder(JsonLoader jsonLoader) {
        ComponentInitializer componentInitializer = initComponentInitializer(jsonLoader);

        EntityBuilder entityBuilder = new JsonEntityBuilder(engine, componentInitializer, jsonLoader);

        entityBuilderProvider = new EntitySummonerProvider(engine, entityBuilder, componentInitializer);

        entityCreator = new EntityCreatorImpl(entityBuilderProvider);
    }

    private ComponentInitializer initComponentInitializer(JsonLoader jsonLoader) {
        ComponentInitializer componentInitializer = new ComponentInitializer();

        ConfigFactory<BodyConfig> bodyConfigFactory = ConfigFactory.cache(new BodyConfigJsonFactory(jsonLoader));

        componentInitializer.addInitializer(new FloatInitializer());
        componentInitializer.addInitializer(new StringInitializer());
        componentInitializer.addInitializer(new IntegerInitializer());
        componentInitializer.addInitializer(new BooleanInitializer());
        componentInitializer.addInitializer(new Vector2Initializer());
        componentInitializer.addInitializer(new AssetTextureInitializer(app.assets));
        componentInitializer.addInitializer(new AssetParticleInitializer(app.assets));
        componentInitializer.addInitializer(new BodyInitializer(world, bodyConfigFactory));

        return componentInitializer;
    }

    private void initEngine() {
        engine.addEntityListener(EntityUserDataSetter.FAMILY, new EntityUserDataSetter());
        engine.addEntityListener(HasFriendsComponentSetter.FAMILY, new HasFriendsComponentSetter());

        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new RemoveAfterDeathSystem());
        engine.addSystem(new DestroyingSystem(world));

        engine.addSystem(new MovementInputSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new CameraFollowingSystem(viewport.getCamera()));

        engine.addSystem(new AimingInputSystem(viewport));
        engine.addSystem(new AimingSystem());

        engine.addSystem(new ConnectionSystem());
        engine.addSystem(new InheritAngleSystem());
        engine.addSystem(new InheritColorSystem());
        engine.addSystem(new InheritDeathSystem());

        engine.addSystem(new ShootingSystem(entityBuilderProvider.provide(SummonerType.Projectile)));

        // engine.addSystem(new ContactMaskSystem());
        engine.addSystem(new ContactDamageSystem());
        engine.addSystem(new ContactBreakSystem());
        engine.addSystem(new LifeSpanSystem());

        //   engine.addSystem(new HealthColoredSystem());
        engine.addSystem(new LowHealthDeathSystem());

        engine.addSystem(new DamagingSystem());
        engine.addSystem(new FadeAfterDeathSystem());
        engine.addSystem(new CollusionSystem());

        engine.addSystem(new SummoningAfterDeathSystem());
        engine.addSystem(new SummonsAfterRemoveSystem());
        engine.addSystem(new SummonsWhileMovingSystem());
        engine.addSystem(new SummonsAfterCollusionSystem());

        engine.addSystem(new SummoningSystem(entityBuilderProvider));
        engine.addSystem(new InvisibleSystem(viewport.getCamera()));

        engine.addSystem(new RenderingSystem(viewport.getCamera(), app.batch));
        engine.addSystem(new DistinctZIndexSystem());

        EntitySystem profile = new GLProfileSystem();
        profile.setProcessing(false);
        engine.addSystem(profile);

        engine.addSystem(new DebugSummonSystem(entityBuilderProvider.getEntityBuilder(), viewport));

        EntitySystem debug = new Box2dDebugSystem(world, viewport);
        debug.setProcessing(false);
        engine.addSystem(debug);

    }

    private void createGrid() {
        Bounds innerBounds = new Bounds(bounds.startX()+1, bounds.startY()+1, bounds.width()-1, bounds.height()-1);
        LimitedPlacer randomPlacer = new UniqueRandomPlacer(innerBounds, random);

        new BorderPlacer(bounds).iterate(spot -> entityCreator.createEntityOn(spot, "Border"));

        entityCreator.setSummonerType(SummonerType.Sprite);
        new SquarePlacer(bounds).iterate(spot -> entityCreator.createEntityOn( spot, 2, "plain"));
        entityCreator.setSummonerType(SummonerType.Default);
        randomPlacer.setMaxPlaced(20);
        randomPlacer.iterate( spot -> entityCreator.createEntityOn(spot, "Box"));
        randomPlacer.iterate( spot -> entityCreator.createEntityOn(spot, "Gasoline"));
        randomPlacer.setMaxPlaced(20);
        randomPlacer.iterate( spot -> entityCreator.createEntityOn(spot, "Bush"));
    }

    private void createTank(String id) {
        Bounds innerBounds = new Bounds(bounds.startX()+1, bounds.startY()+1, bounds.width()-1, bounds.height()-1);
        LimitedPlacer repeatRandomPlacer = new RepeatRandomPlacer(innerBounds, random);

        repeatRandomPlacer.setMaxPlaced(1);
        entityCreator.setSummonerType(SummonerType.Tank);
        repeatRandomPlacer.iterate(spot -> entityCreator.createEntityOn(spot, id));
        entityCreator.setSummonerType(SummonerType.Sprite);
        repeatRandomPlacer.iterate(spot -> entityCreator.createEntityOn(spot, 1, "spawn"));
    }

    @Override
    public void loaded() {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        viewport.getCamera().update();

        if(Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
            Box2dDebugSystem debug = engine.getSystem(Box2dDebugSystem.class);
            debug.setProcessing(!debug.checkProcessing());
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            RenderingSystem render = engine.getSystem(RenderingSystem.class);
            render.setProcessing(!render.checkProcessing());
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            GLProfileSystem render = engine.getSystem(GLProfileSystem.class);
            render.setProcessing(!render.checkProcessing());
        }

        Gdx.app.log("FPS", String.valueOf(Gdx.graphics.getFramesPerSecond()));

        TweenM.getInstance().getManager().update(delta);
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    @Override
    public void dispose() {
        world.dispose();
    }

    @Override
    public TaskLoader getLoadingTask() {
        return TaskLoader.create()
                .add(app.assets::loadParticles, "Particles")
                .add(app.assets::loadTextures, "Textures")
                .loadAssets(app.assets.getAssetManager())
                .add(this::initSocket, "Socket")
                .add(this::createGrid, "Battlefield")
                .get();
    }
}
