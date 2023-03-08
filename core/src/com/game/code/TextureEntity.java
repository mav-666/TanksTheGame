package com.game.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextureEntity extends Actor {
    private TextureRegion texture;

    public TextureEntity () {}

    public TextureEntity (TextureRegion texture) {
        this.texture= texture;
    }

    public void draw(Batch batch, float ParentAlpha) {
        if(texture == null)
            return;

        batch.setColor(getColor());
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        batch.setColor(Color.WHITE);
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }
}
