package com.game.code;

import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.game.code.EntityBuilding.*;
import com.game.code.EntityBuilding.FieldInitializers.*;
import com.game.code.EntityBuilding.Json.BodyConfigJsonFactory;
import com.game.code.EntityBuilding.Json.JsonEntityBuilder;
import com.game.code.EntityBuilding.Json.JsonSupplier;
import com.game.code.FileManagment.InternalJsonSupplier;
import com.game.code.components.*;
import com.game.code.components.ConnectedComponent;
import com.game.code.components.InheritAngleComponent;
import com.game.code.components.InheritColorComponent;
import com.game.code.systems.*;
import com.game.code.utils.CollusionRegister;
import com.game.code.utils.Mappers;
import com.game.code.utils.TweenUtils.ColorAccessor;
import com.game.code.utils.TweenUtils.TweenM;
import com.game.code.utils.TweenUtils.Vector2Accessor;

import java.util.Random;

public class PlayerMovementTests extends ApplicationAdapter {
    private Engine engine;
    private TweenM tween;

    private FitViewport viewport;

    private World world;

    private Box2DDebugRenderer debugRenderer;

    EntityBuilder entityBuilder;

    JsonSupplier jsonSupplier;

    Random random;

    @Override
    public void create() {
        random = new Random(1);

        tween = TweenM.getInstance();
        Tween.registerAccessor(Color.class, new ColorAccessor());
        Tween.registerAccessor(Vector2.class, new Vector2Accessor());

        engine = new PooledEngine();

        viewport = new FitViewport(9, 6);

        world = new World(Vector2.Zero, false);
        world.setContactListener(new CollusionRegister(engine));
        debugRenderer = new Box2DDebugRenderer();

        jsonSupplier = new InternalJsonSupplier();

        entityBuilder = new JsonEntityBuilder(engine, jsonSupplier);

        ConfigFactory<BodyConfig> bodyConfigFactory = ConfigFactory.cache(new BodyConfigJsonFactory(jsonSupplier));

        entityBuilder.addInitializer(new FloatInitializer());
        entityBuilder.addInitializer(new StringInitializer());
        entityBuilder.addInitializer(new IntegerInitializer());
        entityBuilder.addInitializer(new BooleanInitializer());
        entityBuilder.addInitializer(new Vector2Initializer());
        entityBuilder.addInitializer(new TextureRegionInitializer());
        entityBuilder.addInitializer(new ParticleEffectInitializer());
        entityBuilder.addInitializer(new BodyInitializer(world, bodyConfigFactory));


        engine.addEntityListener(Family.all(BodyComponent.class).get(), new EntityUserDataSetter());
        engine.addEntityListener(Family.all(BodyComponent.class, HasFriendsComponent.class).get(), new HasFriendsComponentSetter());

        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new ConnectionSystem());
        engine.addSystem(new DestroyingSystem(world));


        engine.addSystem(new MovementInputSystem());
        engine.addSystem(new MovementSystem());

        engine.addSystem(new AimingInputSystem(viewport));
        engine.addSystem(new AimingSystem());

        engine.addSystem(new InheritAngleSystem());
        engine.addSystem(new InheritColorSystem());
        engine.addSystem(new InheritDeathSystem());

        engine.addSystem(new ShootingInputSystem());
        engine.addSystem(new ShootingSystem(new ProjectileSummoner(entityBuilder)));

        engine.addSystem(new SummoningAfterDeathSystem());
        engine.addSystem(new SummoningSystem(new EntitySummoner(entityBuilder)));

        engine.addSystem(new ContactDamageSystem());
        engine.addSystem(new ContactBreakSystem());
        engine.addSystem(new LifeSpanSystem());

     //   engine.addSystem(new HealthColoredSystem());
        engine.addSystem(new LowHealthDeathSystem());

        engine.addSystem(new FragileParticleSystem());
        engine.addSystem(new ParticleSummoningSystem(new ParticleSummoner(entityBuilder)));

        engine.addSystem(new DamagingSystem());
        engine.addSystem(new FadeAfterDeathSystem());
        engine.addSystem(new EndCollusionSystem());

        engine.addSystem(new RenderingSystem(viewport.getCamera()));

        createTank();
        createTank();

        createObstacle();
        createObstacle();
        createObstacle();
        createObstacle();

       // createFloor(4);

        //createBox(1, 1.25f, new Vector3(0, 0, 4), 0);

       // createBox(1.25f, 1, new Vector3(1.5f, 2, 4), 45);
    }


    private void createTank() {
        Entity cab = createCab();

        Entity head = createHead();

        ConnectedComponent HeadConnectedC = entityBuilder.getComponent(ConnectedComponent.class);
        HeadConnectedC.target = Mappers.getInstance().get(BodyComponent.class).get(cab).body;

        entityBuilder.getComponent(InheritColorComponent.class).target = cab;
        entityBuilder.getComponent(InheritDeathComponent.class).target = cab;

        createBarrel();

        TextureComponent textureC = entityBuilder.getComponent(TextureComponent.class);

        ConnectedComponent BarrelConnectedC = entityBuilder.getComponent(ConnectedComponent.class);
        BarrelConnectedC.target = Mappers.getInstance().get(BodyComponent.class).get(cab).body;
        BarrelConnectedC.connectionPoint.set(0, -textureC.height/2);

        entityBuilder.getComponent(InheritAngleComponent.class).target = head;
        entityBuilder.getComponent(InheritColorComponent.class).target = cab;
        entityBuilder.getComponent(InheritDeathComponent.class).target = cab;
    }

    private Entity createCab() {
        entityBuilder.build(Entities.Cab.name());
        entityBuilder.getEntity().flags = 2;

        engine.addEntity(entityBuilder.getEntity());

        return entityBuilder.getEntity();
    }

    private Entity createHead() {
        entityBuilder.build(Entities.Head.name());

        Entity head = entityBuilder.getEntity();

        TextureComponent textureC = entityBuilder.getComponent(TextureComponent.class);

        textureC.height = 0.5f;
        textureC.width = 0.5f;

        engine.addEntity(head);

        entityBuilder.getEntity().flags = 3;

        return head;
    }

    private void createBarrel() {
        entityBuilder.build(Entities.Barrel.name());

        TextureComponent textureC = entityBuilder.getComponent(TextureComponent.class);
        textureC.height = 0.75f;
        textureC.width = 0.2f;
        entityBuilder.getComponent(TransformComponent.class).origin.set(0, -textureC.height/2);

        ProjectileTemplateComponent projectileC = entityBuilder.getComponent(ProjectileTemplateComponent.class);
        projectileC.shootingPoint.set(0, 0.5f);

        engine.addEntity(entityBuilder.getEntity());

    }

    private void createObstacle() {
        String entityName = random.nextBoolean()? Entities.Box.name() : Entities.Gasoline.name();

        entityBuilder.build(entityName);

        BodyComponent bodyC = entityBuilder.getComponent(BodyComponent.class);
        bodyC.body.setTransform(random.nextInt(-5, 5), random.nextInt(-5, 5), 0);

        engine.addEntity(entityBuilder.getEntity());
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        viewport.getCamera().update();

        debugRenderer.render(world, viewport.getCamera().combined);

        if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            entityBuilder.build(Entities.Explosion.name());
            engine.addEntity(entityBuilder.getEntity());
        }



        float deltaTime = Gdx.graphics.getDeltaTime();

        tween.getManager().update(deltaTime);
        engine.update(deltaTime);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    @Override
    public void dispose() {
        world.dispose();
    }
}
