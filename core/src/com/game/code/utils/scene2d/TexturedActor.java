package com.game.code.utils.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TexturedActor extends Actor {
    private final TextureRegion texture;

    public TexturedActor(TextureRegion texture) {
        this.texture = texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        batch.draw(
                texture,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );
        batch.setColor(Color.WHITE);

    }
}
