package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class ScrollComponent implements Component, Pool.Poolable {
    public Array<Entity> allItems = new Array<>();
    public int currentItemGroup = 0;
    public float padding = 0.3f;
    public int visibleItems = 1;

    @Override
    public void reset() {
        allItems.clear();
        currentItemGroup = 0;
        visibleItems = 1;
    }
}
