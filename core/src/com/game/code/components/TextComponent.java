package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.github.tommyettinger.textra.TextraLabel;

public class TextComponent implements Component, Pool.Poolable {
    public TextraLabel label;
    public Vector2 offset = new Vector2();

    @Override
    public void reset() {
        label = null;
        offset.set(0,0);
    }
}