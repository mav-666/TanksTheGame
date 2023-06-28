package com.game.code.EntityBuilding.FieldInitializers;

import com.badlogic.gdx.math.Vector2;

public class Vector2Initializer extends ArrayFieldInitializer<Vector2, Float> {

    @Override
    public Vector2 getInitBy(Float[] config) {
        return new Vector2(config[0], config[1]);
    }
}
