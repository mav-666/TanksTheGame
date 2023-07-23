package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Pool;

public class WidgetComponent implements Component, Pool.Poolable {
    public Actor widget;
    public Vector2 offset = new Vector2();

    @Override
    public void reset() {
        widget = null;
        offset.set(0,0);
    }
}
