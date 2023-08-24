package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.FieldInitializers.TankConfig.*;
import com.game.code.components.*;
import com.game.code.utils.Mappers;

import java.util.Arrays;

public class TankSummoner extends EntitySummoner {
    private boolean isPlayer;
    private String id;
    private String name;
    private TankConfig tankConfig;

    private float tankWidth;
    private float tankHeight;

    public TankSummoner(EntityBuilder entityBuilder, Engine engine) {
        super(entityBuilder, engine);
    }

    @Override
    public Entity summonBy(Entity summoner) {
        String[] entityName = Mappers.get(SummonsNowComponent.class, summoner).entityName.split(" ");
        isPlayer = Arrays.asList(entityName).contains("player");
        name = entityName.length >= 2 ? entityName[1] : "";
        id = entityName[0];

        tankConfig = Mappers.has(TankTemplateComponent.class, summoner) ? Mappers.get(TankTemplateComponent.class, summoner).tankConfig : new TankConfig();

        Entity cab = createCab();

        initTransformBy(summoner);

        tankWidth = entityBuilder.getComponent(TextureComponent.class).width;
        tankHeight = entityBuilder.getComponent(TextureComponent.class).height;

        Entity head = createHead();

        ConnectedComponent HeadConnectedC = entityBuilder.getComponent(ConnectedComponent.class);
        HeadConnectedC.target = Mappers.get(BodyComponent.class, cab).body;

        entityBuilder.getComponent(InheritColorComponent.class).target = cab;
        entityBuilder.getComponent(InheritDeathComponent.class).target = cab;

        createBarrel();

        TextureComponent textureC = entityBuilder.getComponent(TextureComponent.class);

        ConnectedComponent BarrelConnectedC = entityBuilder.getComponent(ConnectedComponent.class);
        BarrelConnectedC.target = Mappers.get(BodyComponent.class, cab).body;
        BarrelConnectedC.connectionPoint.set(0, -textureC.height/2);

        entityBuilder.getComponent(InheritAngleComponent.class).target = head;
        entityBuilder.getComponent(InheritColorComponent.class).target = cab;
        entityBuilder.getComponent(InheritDeathComponent.class).target = cab;

        return cab;
    }

    private Entity createCab() {
        CabConfig cabConfig = tankConfig.cabConfig();

        entityBuilder.build(cabConfig.cabName());
        entityBuilder.getEntity().flags = 2;

        MobilityComponent mobility = entityBuilder.getComponent(MobilityComponent.class);
        mobility.agility = cabConfig.agility();
        mobility.movementSpeed = cabConfig.movementSpeed();

        HealthComponent health = entityBuilder.getComponent(HealthComponent.class);
        health.currentHP = cabConfig.HP();

        if(isPlayer) {
            entityBuilder.getComponent(PlayerComponent.class);
            entityBuilder.getComponent(CameraFollowedComponent.class);
        } else {
            entityBuilder.getComponent(IdComponent.class).id = id;
        }
        engine.addEntity(entityBuilder.getEntity());

        return entityBuilder.getEntity();
    }

    private Entity createHead() {
        HeadConfig headConfig = tankConfig.headConfig();

        entityBuilder.build(headConfig.headName());

        CanonComponent canon = entityBuilder.getComponent(CanonComponent.class);
        canon.rotationSpeed = headConfig.rotationSpeed();

        if(isPlayer) {
            entityBuilder.getComponent(PlayerComponent.class);
        } else {
            entityBuilder.getComponent(IdComponent.class).id = id;
        }

        entityBuilder.getComponent(NameComponent.class).name = name;

        TextureComponent textureC = entityBuilder.getComponent(TextureComponent.class);

        textureC.height = tankHeight / 2;
        textureC.width = tankWidth / 2;

        entityBuilder.getEntity().flags = 3;

        engine.addEntity(entityBuilder.getEntity());

        return entityBuilder.getEntity();
    }

    private void createBarrel() {
        BarrelConfig barrelConfig = tankConfig.barrelConfig();

        entityBuilder.build(barrelConfig.barrelName());

        RechargingComponent recharging = entityBuilder.getComponent(RechargingComponent.class);
        recharging.seconds = barrelConfig.rechargeSeconds();

        initProjectileTemplate(barrelConfig.projectileConfig());


        if(isPlayer) {
            entityBuilder.getComponent(PlayerComponent.class);
        } else {
            entityBuilder.getComponent(IdComponent.class).id = id;
        }

        TextureComponent textureC = entityBuilder.getComponent(TextureComponent.class);
        textureC.height = tankHeight / 1.3f;
        textureC.width = tankWidth / 5;

        entityBuilder.getComponent(TransformComponent.class).origin.set(0, -textureC.height/2);

        ProjectileTemplateComponent projectileC = entityBuilder.getComponent(ProjectileTemplateComponent.class);
        projectileC.shootingPoint.set(0, textureC.height/2);

        engine.addEntity(entityBuilder.getEntity());
    }

    private void initProjectileTemplate(ProjectileConfig projectileConfig) {
        ProjectileTemplateComponent projectileTemplate = entityBuilder.getComponent(ProjectileTemplateComponent.class);
        projectileTemplate.radius = projectileConfig.radius();
        projectileTemplate.recoil = projectileConfig.recoil();
        projectileTemplate.entityName = projectileConfig.projectileName();
        projectileTemplate.speed = projectileConfig.speed();
        projectileTemplate.contactDamage = projectileConfig.contactDamage();
    }

    @Override
    public SummonerType getType() {
        return SummonerType.Tank;
    }
}
