package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class ScrollsComponent implements Component, Pool.Poolable {
    public int scrollingDirection = 0;

    @Override
    public void reset() {
        scrollingDirection = 0;
    }
}
