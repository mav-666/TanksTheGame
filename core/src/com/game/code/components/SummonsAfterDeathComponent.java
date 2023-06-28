package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class SummonsAfterDeathComponent implements Component, Pool.Poolable {
    public String entityName = "";

    @Override
    public void reset() {
        entityName = "";
    }
}
