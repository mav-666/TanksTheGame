package com.game.code;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.code.Entity.Entity;
import com.game.code.Entity.Projectile;

public class BodyBuilder {
    private static short LastBodyIndex= 0;
    public static Body createBody(
          World world,
          Entity userData,
          Vector2 pos,
          BodyDef.BodyType type,
          float mass)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos);
        bodyDef.type = type;
        bodyDef.fixedRotation = true;

        Body body = world.createBody(bodyDef);

        MassData massData = new MassData();
        massData.mass = mass;
        massData.center.set(body.getLocalCenter());

        body.setMassData(massData);

        body.setUserData(new BodyData(userData, (userData instanceof Projectile)? 0 : ++LastBodyIndex));

        return body;
    }

    public static Fixture createFixture(Body body, Entity userData, Shape shape, short category, short mask, short groupIndex,
                                     float density, float restitution) {

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;

        fixtureDef.filter.maskBits = mask;
        fixtureDef.filter.categoryBits = category;
        fixtureDef.filter.groupIndex = groupIndex;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(body.getUserData());
        ((BodyData) fixture.getUserData()).owner = userData;

        return fixture;
    }


    public static Fixture createFixture(Body body, Entity userData, Shape shape, short category, short mask, float density, float restitution) {
        return createFixture(body, userData, shape, category, mask, (short) 0, density, restitution);
    }
}
