package com.game.code;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.game.code.Entity.DelayedEntity;
import com.game.code.Entity.Entity;
import com.game.code.Entity.Projectile;

import java.util.HashMap;

public class BodyHandler {
    private final World world;

    private int lastBodyIndex;

    HashMap<DelayedEntity, BodyDef> queueForCreation;
    Array<Body> queueForActivation;
    Array<Entity> queueForDispose;

    public BodyHandler(World world) {
        this.world = world;

        lastBodyIndex = 0;

        queueForCreation = new HashMap<>();
        queueForActivation = new Array<>();
        queueForDispose = new Array<>();
    }

    public void activateBodies() {
        queueForActivation.forEach(body -> body.setActive(true));
        queueForActivation.clear();
    }


    public void disposeBodies() {
        queueForDispose.forEach(Entity::destroy);
        queueForDispose.clear();
    }

    public void createDelayed() {
        queueForCreation.keySet().forEach((delayedEntity ->
                delayedEntity.initBody(createBody(delayedEntity,queueForCreation.get(delayedEntity)), this)));
        queueForCreation.clear();
    }

    public Body requestCreation(Entity client, Vector2 pos, BodyDef.BodyType type) {
        BodyDef bodyDef = createBodyDef(pos, type);

        return createBody(client, bodyDef);
    }

    public void requestCreation(DelayedEntity client, Vector2 pos, BodyDef.BodyType type) {
        BodyDef bodyDef = createBodyDef(pos, type);

        queueForCreation.put(client, bodyDef);
    }

    public void requestDisposal(Entity entity) {
        queueForDispose.add(entity);
        ((BodyData) entity.getBody().getUserData()).flaggedForDispose = true;
    }

    private Body createBody(Entity client, BodyDef bodyDef) {
        Body body =  world.createBody(bodyDef);

        body.setUserData(new BodyData(this, client, (client instanceof Projectile)? 0 : ++lastBodyIndex));

        queueForActivation.add(body);

        return body;
    }

    private BodyDef createBodyDef(Vector2 pos, BodyDef.BodyType type) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos);
        bodyDef.fixedRotation = true;
        bodyDef.type = type;
        bodyDef.active = false;

        return bodyDef;
    }

    public Fixture createFixture(Body body, Entity userData, Shape shape, short category, short mask, short groupIndex, boolean isSensor,
                                     float density, float restitution) {

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;

        fixtureDef.filter.maskBits = mask;
        fixtureDef.filter.categoryBits = category;
        fixtureDef.filter.groupIndex = groupIndex;
        fixtureDef.isSensor = isSensor;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(body.getUserData());
        ((BodyData) fixture.getUserData()).owner = userData;

        return fixture;
    }

    public Fixture createFixture(Body body, Entity userData, Shape shape, short category, short mask, boolean isSensor,
                                        float density, float restitution) {
        return createFixture(body, userData, shape, category, mask, (short) 0, isSensor, density, restitution);
    }
}
