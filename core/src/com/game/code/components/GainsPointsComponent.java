package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class GainsPointsComponent implements Component, Pool.Poolable {
    public int points = 0;

    @Override
    public void reset() {
        points = 0;
    }
}
