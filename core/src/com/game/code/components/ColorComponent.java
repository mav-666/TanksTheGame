package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Pool;

public class ColorComponent implements Component, Pool.Poolable {
    public Color color = new Color(Color.WHITE);

    @Override
    public void reset() {
        color = new Color(Color.WHITE);
    }
}
