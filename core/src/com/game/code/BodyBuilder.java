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
          Shape shape,
          BodyDef.BodyType type,
          short category, short mask, short groupIndex,
          float density, float restitution, float mass)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos);
        bodyDef.type = type;
        bodyDef.fixedRotation = true;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;

        fixtureDef.filter.maskBits = mask;
        fixtureDef.filter.categoryBits = category;
        fixtureDef.filter.groupIndex = groupIndex;

        Body body = world.createBody(bodyDef);

        MassData massData = new MassData();
        massData.mass = mass;
        massData.center.set(body.getLocalCenter());

        body.setMassData(massData);

        body.createFixture(fixtureDef);

        body.setUserData(new BodyData(userData, (userData instanceof Projectile)? 0 : ++LastBodyIndex));

        body.getFixtureList().first().setUserData(body.getUserData());

        return body;
    }

    public static Body createBody(World world,
                                  Entity userData,
                                  Vector2 pos,
                                  Shape shape,
                                  BodyDef.BodyType type,
                                  short category, short mask,
                                  float density, float restitution, float mass) {
        return createBody(world, userData, pos, shape, type, category, mask, (short) 0, density, restitution, mass);
    }
}
