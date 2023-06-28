package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class HealthComponent implements Component, Pool.Poolable {
    public float health = 0;

    @Override
    public void reset() {
        health = 0;
    }
}
