package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class ContactDamageComponent implements Component, Pool.Poolable {
    public float damage = 0;

    @Override
    public void reset() {
        damage = 0;
    }
}
