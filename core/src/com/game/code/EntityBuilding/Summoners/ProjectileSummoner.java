package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.components.*;
import com.game.code.utils.FriendsMaker;

public class ProjectileSummoner extends EntityBuilderSummoner {

    private final FriendsMaker friendsMaker;

    private TransformComponent shooterTransform;
    private ProjectileTemplateComponent projectileTemplate;

    public ProjectileSummoner(EntityBuilder entityBuilder, Engine engine) {
        super(entityBuilder, engine);

        friendsMaker = new FriendsMaker();
    }

    @Override
    public Entity summonBy(Entity summoner) {
        projectileTemplate = mappers.get(ProjectileTemplateComponent.class).get(summoner);
        shooterTransform = mappers.get(TransformComponent.class).get(summoner);

        entityBuilder.build(projectileTemplate.entityName);

        entityBuilder.getEntity().flags = 5;

        Vector2 shootingPoint = calculateShootingPoint();

        initBody(shootingPoint);
        friendsMaker.makeFriendsTo(summoner, entityBuilder.getEntity());
        initPosition(shootingPoint);
        setContactDamage();
        setTextureSize();

        engine.addEntity(entityBuilder.getEntity());

        return entityBuilder.getEntity();
    }


    private void initBody(Vector2 shootingPoint) {
        Body projectileBody = entityBuilder.getComponent(BodyComponent.class).body;

        projectileBody.setTransform(shootingPoint, (float) Math.toRadians(shooterTransform.degAngle));

        projectileBody.getFixtureList().first().getShape().setRadius(projectileTemplate.radius);

        projectileBody.setLinearVelocity(new Vector2(0, projectileTemplate.speed).rotateRad(projectileBody.getAngle()));
    }



    private void initPosition(Vector2 shootingPoint) {
        TransformComponent transformComponent = entityBuilder.getComponent(TransformComponent.class);

        transformComponent.position.set(shootingPoint);
        transformComponent.zIndex = shooterTransform.zIndex + 1;
        transformComponent.degAngle = shooterTransform.degAngle;
    }

    private Vector2 calculateShootingPoint() {
        Vector2 shootingPoint = projectileTemplate.shootingPoint.cpy();

        shootingPoint.rotateAroundDeg(shooterTransform.origin, shooterTransform.degAngle);

        return shootingPoint.add(shooterTransform.position);
    }

    private void setContactDamage() {
        ContactDamageComponent contactDamage = entityBuilder.getComponent(ContactDamageComponent.class);

        contactDamage.damage = projectileTemplate.contactDamage;
    }

    private void setTextureSize() {
        TextureComponent texture = entityBuilder.getComponent(TextureComponent.class);

        texture.width = texture.height = projectileTemplate.radius * 2;
    }
}
