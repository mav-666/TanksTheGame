package com.game.code.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.code.Application;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.FieldInitializers.*;
import com.game.code.EntityBuilding.FieldInitializers.Box2dBodyCreation.BodyConfig;
import com.game.code.EntityBuilding.FieldInitializers.Config.ConfigFactory;
import com.game.code.EntityBuilding.Json.BodyConfigJsonFactory;
import com.game.code.EntityBuilding.Json.JsonEntityBuilder;
import com.game.code.EntityBuilding.Json.JsonLoader;
import com.game.code.EntityBuilding.Summoners.*;
import com.game.code.EntityBuilding.EntityCreator;
import com.game.code.EntityBuilding.EntityCreatorImpl;
import com.game.code.screens.Loading.LoadableScreen;
import com.game.code.screens.Loading.TaskLoader;
import com.game.code.systems.Box2dDebugSystem;
import com.game.code.systems.RenderingSystem;
import com.game.code.utils.CollusionRegister;
import com.game.code.utils.CustomizableEngine;
import com.game.code.utils.Mappers;
import com.game.code.utils.TweenUtils.TweenM;

public abstract class EngineScreen extends AbstractScreen implements LoadableScreen {
    protected final CustomizableEngine engine = new CustomizableEngine(app.componentInitializer);
    protected final World world = new World(Vector2.Zero, false);

    protected EntityBuilder entityBuilder;
    public EntitySummonerProvider entitySummonerProvider;
    public EntityCreator entityCreator;

    protected Viewport viewport;

    public EngineScreen(Application app) {
        super(app);

        world.setContactListener(new CollusionRegister(engine));

        entityBuilder = initEntityBuilder(app.jsonLoader);
    }

    private EntityBuilder initEntityBuilder(JsonLoader jsonLoader) {
        ComponentInitializer componentInitializer = initComponentInitializer();

        EntityBuilder entityBuilder = new JsonEntityBuilder(engine, componentInitializer, jsonLoader);

        entitySummonerProvider = new EntitySummonerProvider(new EntitySummoner(entityBuilder, engine));

        entitySummonerProvider.add(new SpriteSummoner(entityBuilder, engine, componentInitializer));
        entitySummonerProvider.add(new ParticleSummoner(entityBuilder, engine, componentInitializer));
        entitySummonerProvider.add(new ProjectileSummoner(entityBuilder, engine));
        entitySummonerProvider.add(new TankSummoner(entityBuilder, engine));
        entitySummonerProvider.add(new ButtonSummoner(entityBuilder, engine, componentInitializer));

        entityCreator = new EntityCreatorImpl(entitySummonerProvider, entityBuilder);

        return entityBuilder;
    }

    private ComponentInitializer initComponentInitializer() {
        ConfigFactory<BodyConfig> bodyConfigFactory = ConfigFactory.cache(new BodyConfigJsonFactory(app.jsonLoader));
        app.componentInitializer.addInitializer(new BodyInitializer(world, bodyConfigFactory));

        return app.componentInitializer;
    }

    @Override
    public TaskLoader getLoadingTask() {
        return TaskLoader.create()
                .add(app.assets::loadParticles, "Particles")
                .add(app.assets::loadTextures, "Textures")
                .loadAssets(app.assets.getAssetManager())
                .add(this::initEngine, "engine")
                .add(this::initAim, "aim")
                .get();
    }

    protected void initEngine() {
        this.viewport = initViewport();
        engine.includeBasic(entitySummonerProvider, world, viewport, app.batch);
        Mappers.getInstance().setEngine(engine);
    }

    protected abstract Viewport initViewport();

    private void initAim() {
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);
        entityBuilder.build("Aim");
        engine.addEntity(entityBuilder.getEntity());
    }

    @Override
    public void show() {
        ConfigFactory<BodyConfig> bodyConfigFactory = ConfigFactory.cache(new BodyConfigJsonFactory(app.jsonLoader));
        app.componentInitializer.addInitializer(new BodyInitializer(world, bodyConfigFactory));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        viewport.getCamera().update();

        if(Gdx.input.isKeyJustPressed(Input.Keys.Y) && engine.getSystem(Box2dDebugSystem.class) != null) {
            Box2dDebugSystem debug = engine.getSystem(Box2dDebugSystem.class);
            debug.setProcessing(!debug.checkProcessing());
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.T) && engine.getSystem(RenderingSystem.class) != null) {
            RenderingSystem render = engine.getSystem(RenderingSystem.class);
            render.setProcessing(!render.checkProcessing());
        }

        engine.update(delta);
        TweenM.getManager().update(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        viewport.update(width, height, false);
    }

    @Override
    public void dispose() {
        TweenM.getManager().killAll();
        engine.removeAllEntities();
        engine.removeAllSystems();
        world.dispose();
    }
}
