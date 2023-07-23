package com.game.code.EntityBuilding.FieldInitializers;

import com.badlogic.gdx.physics.box2d.*;
import com.game.code.EntityBuilding.FieldInitializers.Box2dBodyCreation.BodyConfig;
import com.game.code.EntityBuilding.FieldInitializers.Config.ConfigFactory;

public class BodyInitializer extends FieldInitializer<Body, String> {

    private final World world;

    private final ConfigFactory<BodyConfig> bodyConfigFactory;

    public BodyInitializer(World world, ConfigFactory<BodyConfig> bodyConfigFactory) {
        this.world = world;
        this.bodyConfigFactory = bodyConfigFactory;
    }

    @Override
    public Body getInitBy(String config) {
        BodyConfig bodyConfig = bodyConfigFactory.get(config);

        Body initBody = world.createBody(bodyConfig.bodyDef());

        bodyConfig.fixtureDefs().forEach(initBody::createFixture);

        initBody.getMassData().mass = bodyConfig.mass();

        return initBody;
    }
}
