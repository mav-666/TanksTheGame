package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class CanonComponent implements Component, Pool.Poolable {
    public float rotationSpeed = 0;

    @Override
    public void reset() {
        rotationSpeed = 0;
    }
}
