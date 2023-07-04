package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class ForcedComponent implements Component, Pool.Poolable {
    public Vector2 force = new Vector2(0,0);

    @Override
    public void reset() {
        force.set(0,0);
    }
}
