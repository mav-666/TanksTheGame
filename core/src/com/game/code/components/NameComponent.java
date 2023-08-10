package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class NameComponent implements Component, Pool.Poolable {
    public String name = "";
    @Override
    public void reset() {
        name = "";
    }
}
