package com.game.code.EntityBuilding.FieldInitializers.Box2dBodyCreation;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.game.code.EntityBuilding.Config;

public record BodyConfig(
        BodyDef bodyDef,
        float mass,
        Array<FixtureDef> fixtureDefs
) implements Config {}
