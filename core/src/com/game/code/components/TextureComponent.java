package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class TextureComponent implements Component, Pool.Poolable {
    public TextureRegion textureRegion;

    public Color color = new Color(Color.WHITE);

    public Vector2 offset = new Vector2(0, 0);

    public boolean isHidden = false;

    public float width = 0;
    public float height = 0;

    public float scaleX = 1;
    public float scaleY = 1;

    @Override
    public void reset() {
        textureRegion = null;
        color.set(Color.WHITE);
        offset.set(0,0);
        isHidden = false;
        width = 0;
        height = 0;
        scaleX = 1;
        scaleY = 1;
    }
}
