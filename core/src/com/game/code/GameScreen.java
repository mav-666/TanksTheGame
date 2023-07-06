package com.game.code;

import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.core.Engine;
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
import com.game.code.EntityBuilding.Json.JsonSupplier;
import com.game.code.EntityBuilding.Summoners.ProjectileSummoner;
import com.game.code.EntityBuilding.Summoners.EntitySummonerProvider;
import com.game.code.EntityBuilding.battlefiled.*;
import com.game.code.FileManagment.InternalJsonSupplier;
import com.game.code.UI.screens.AbstractScreen;
import com.game.code.UI.screens.Loading.LoadableScreen;
import com.game.code.UI.screens.Loading.TaskLoader;
import com.game.code.components.*;
import com.game.code.systems.*;
import com.game.code.utils.BoundedCamera;
import com.game.code.utils.CollusionRegister;
import com.game.code.utils.TweenUtils.ColorAccessor;
import com.game.code.utils.TweenUtils.TweenM;
import com.game.code.utils.TweenUtils.Vector2Accessor;

import java.util.Random;

public class GameScreen extends AbstractScreen implements LoadableScreen {
    private final Engine engine;
    private final TweenM tween;
    private final Random random;

    private final FitViewport viewport;
    private final BoundedCamera boundedCamera;
    private final World world;

    private ComponentInitializer componentInitializer;
    private final EntitySummonerProvider entityBuilderProvider;
    private final EntityBuilder entityBuilder;

    private final JsonSupplier jsonSupplier;

    private final Bounds bounds = new Bounds(50, 50);

    public GameScreen(Application app) {
        super(app);

        random = new Random(1);

        tween = TweenM.getInstance();
        Tween.registerAccessor(Color.class, new ColorAccessor());
        Tween.registerAccessor(Vector2.class, new Vector2Accessor());

        engine = new PooledEngine();

        boundedCamera = new BoundedCamera(bounds);
        viewport = new FitViewport(9, 6, boundedCamera);

        world = new World(Vector2.Zero, false);
        world.setContactListener(new CollusionRegister(engine));

        jsonSupplier = new InternalJsonSupplier();

        initComponentInitializer();

        entityBuilder = new JsonEntityBuilder(engine, componentInitializer, jsonSupplier);

        entityBuilderProvider = new EntitySummonerProvider(engine, entityBuilder, componentInitializer);

        initEngine();
    }

    private void initEngine() {
        engine.addEntityListener(Family.all(BodyComponent.class).get(), new EntityUserDataSetter());
        engine.addEntityListener(Family.all(BodyComponent.class, HasFriendsComponent.class).get(), new HasFriendsComponentSetter());

        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new ConnectionSystem());
        engine.addSystem(new DestroyingSystem(world));


        engine.addSystem(new MovementInputSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new CameraFollowingSystem(boundedCamera));

        engine.addSystem(new AimingInputSystem(viewport));
        engine.addSystem(new AimingSystem());

        engine.addSystem(new InheritAngleSystem());
        engine.addSystem(new InheritColorSystem());
        engine.addSystem(new InheritDeathSystem());

        engine.addSystem(new ShootingInputSystem());
        engine.addSystem(new ShootingSystem(new ProjectileSummoner(entityBuilder, engine)));


        engine.addSystem(new ContactDamageSystem());
        engine.addSystem(new ContactBreakSystem());
        engine.addSystem(new LifeSpanSystem());

        //   engine.addSystem(new HealthColoredSystem());
        engine.addSystem(new LowHealthDeathSystem());

        engine.addSystem(new DamagingSystem());
        engine.addSystem(new FadeAfterDeathSystem());
        engine.addSystem(new EndCollusionSystem());

        engine.addSystem(new SummoningAfterDeathSystem());
        engine.addSystem(new SummonsAfterRemoveSystem());

        engine.addSystem(new SummoningSystem(entityBuilderProvider));
        engine.addSystem(new InvisibleSystem(boundedCamera));
        engine.addSystem(new RenderingSystem(viewport.getCamera(), app.batch));

        EntitySystem debug = new Box2dDebugSystem(world, viewport);
        engine.addSystem(new GLProfileSystem());
        debug.setProcessing(false);
        engine.addSystem(debug);



    }

    private void initComponentInitializer() {
        componentInitializer = new ComponentInitializer();

        ConfigFactory<BodyConfig> bodyConfigFactory = ConfigFactory.cache(new BodyConfigJsonFactory(jsonSupplier));

        componentInitializer.addInitializer(new FloatInitializer());
        componentInitializer.addInitializer(new StringInitializer());
        componentInitializer.addInitializer(new IntegerInitializer());
        componentInitializer.addInitializer(new BooleanInitializer());
        componentInitializer.addInitializer(new Vector2Initializer());
        componentInitializer.addInitializer(new AssetTextureInitializer(app.assets));
        componentInitializer.addInitializer(new AssetParticleInitializer(app.assets));
        componentInitializer.addInitializer(new BodyInitializer(world, bodyConfigFactory));
    }


    private void createGrid() {
        BattleFieldTemplate battleFieldTemplate = new GridTemplate();

        AbstractFiller filler = new SingleFiller();
        Placer randomPlacer = new RandomPlacer(new Bounds(bounds.startX()+1, bounds.startY()+1, bounds.width()-1, bounds.height()-1), random);

        filler.addFilter(battleFieldTemplate::isOccupied);

        filler.start(battleFieldTemplate);
        filler.fillBy(new BorderPlacer(bounds), -1, new EntityTemplate(SummonerType.Default, "Border"));
        filler.fillBy(new SquarePlacer(bounds),1, new EntityTemplate(SummonerType.Sprite, "plain"));
        filler.setMaxPlaced(1000);
        filler.fillBy(randomPlacer, -1, new EntityTemplate(SummonerType.Default, "Box"));
        filler.fillBy(randomPlacer, -1, new EntityTemplate(SummonerType.Default, "Gasoline"));
        filler.setMaxPlaced(1);
        filler.fillBy(randomPlacer, 0, new EntityTemplate(SummonerType.Tank, ""));


       BattlefieldFactory battlefieldFactory = new BattlefieldFactoryImpl(entityBuilderProvider);

       battlefieldFactory.create(battleFieldTemplate);
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

        if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            entityBuilder.build("Explosion");
            engine.addEntity(entityBuilder.getEntity());
        }

        Gdx.app.log("FPS", String.valueOf(Gdx.graphics.getFramesPerSecond()));

        tween.getManager().update(delta);
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
                .add(this::createGrid, "Battlefield")
                .get();
    }
}
