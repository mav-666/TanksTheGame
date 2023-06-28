package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class MobilityComponent implements Component, Pool.Poolable {
    public float movementSpeed = 0;
    public float agility = 0;

    @Override
    public void reset() {
        movementSpeed = 0;
        agility = 0;
    }
}
