package com.game.code.EntityBuilding.FieldInitializers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureRegionInitializer extends FieldInitializer<TextureRegion, String> {

    @Override
    public TextureRegion getInitBy(String config) {
        return new TextureRegion(new Texture(Gdx.files.internal(config)));
    }
}
