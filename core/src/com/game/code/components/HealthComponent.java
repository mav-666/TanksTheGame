package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class HealthComponent implements Component, Pool.Poolable {
    public float maxHP = -1;
    public float currentHP = 0;

    @Override
    public void reset() {
        maxHP = -1;
        currentHP = 0;
    }
}
