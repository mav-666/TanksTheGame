package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.components.*;

public class TankSummoner extends EntitySummoner {
    private float tankWidth;
    private float tankHeight;

    private enum TankParts{
        Cab,
        Head,
        Barrel,
    }

    public TankSummoner(EntityBuilder entityBuilder, Engine engine) {
        super(entityBuilder, engine);
    }

    @Override
    public Entity summonBy(Entity summoner) {
        Entity cab = createCab();

        initTransformBy(summoner);

        tankWidth = entityBuilder.getComponent(TextureComponent.class).width;
        tankHeight = entityBuilder.getComponent(TextureComponent.class).height;
        
        Entity head = createHead();

        ConnectedComponent HeadConnectedC = entityBuilder.getComponent(ConnectedComponent.class);
        HeadConnectedC.target = mappers.get(BodyComponent.class).get(cab).body;

        entityBuilder.getComponent(InheritColorComponent.class).target = cab;
        entityBuilder.getComponent(InheritDeathComponent.class).target = cab;

        createBarrel();

        TextureComponent textureC = entityBuilder.getComponent(TextureComponent.class);

        ConnectedComponent BarrelConnectedC = entityBuilder.getComponent(ConnectedComponent.class);
        BarrelConnectedC.target = mappers.get(BodyComponent.class).get(cab).body;
        BarrelConnectedC.connectionPoint.set(0, -textureC.height/2);

        entityBuilder.getComponent(InheritAngleComponent.class).target = head;
        entityBuilder.getComponent(InheritColorComponent.class).target = cab;
        entityBuilder.getComponent(InheritDeathComponent.class).target = cab;

        return cab;
    }

    private Entity createCab() {
        entityBuilder.build(TankParts.Cab.name());
        entityBuilder.getEntity().flags = 2;

        engine.addEntity(entityBuilder.getEntity());

        return entityBuilder.getEntity();
    }

    private Entity createHead() {
        entityBuilder.build(TankParts.Head.name());

        TextureComponent textureC = entityBuilder.getComponent(TextureComponent.class);

        textureC.height = tankHeight / 2;
        textureC.width = tankWidth / 2;

        entityBuilder.getEntity().flags = 3;

        engine.addEntity(entityBuilder.getEntity());

        return entityBuilder.getEntity();
    }

    private void createBarrel() {
        entityBuilder.build(TankParts.Barrel.name());

        TextureComponent textureC = entityBuilder.getComponent(TextureComponent.class);
        textureC.height = tankHeight / 1.3f;
        textureC.width = tankWidth / 5;

        entityBuilder.getComponent(TransformComponent.class).origin.set(0, -textureC.height/2);

        ProjectileTemplateComponent projectileC = entityBuilder.getComponent(ProjectileTemplateComponent.class);
        projectileC.shootingPoint.set(0, textureC.height/2);

        engine.addEntity(entityBuilder.getEntity());
    }
}
