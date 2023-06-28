package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public class InheritAngleComponent implements Component, Pool.Poolable {
    public Entity target;

    @Override
    public void reset() {
        target = null;
    }
}
