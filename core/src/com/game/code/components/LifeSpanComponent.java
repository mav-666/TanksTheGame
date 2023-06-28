package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class LifeSpanComponent implements Component, Pool.Poolable {
    public float seconds = 0;

    @Override
    public void reset() {
        seconds = 0;
    }
}
