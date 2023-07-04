package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class CollusionComponent implements Component, Pool.Poolable {
    public Array<Entity> involved = new Array<>();

    @Override
    public void reset() {
        involved.clear();
    }
}
