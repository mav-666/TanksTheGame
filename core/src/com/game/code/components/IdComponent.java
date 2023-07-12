package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class IdComponent implements Component, Pool.Poolable {
    public String id = "";

    @Override
    public void reset() {
        id = "";
    }
}
