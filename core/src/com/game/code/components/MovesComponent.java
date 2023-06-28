package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class MovesComponent implements Component, Pool.Poolable {
    public int movementDirection = 0;
    public int turingDirection = 0;

    @Override
    public void reset() {
        movementDirection = 0;
        turingDirection = 0;
    }
}

