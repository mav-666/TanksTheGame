package com.game.code.EntityBuilding.FieldInitializers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SkinColorInitializer extends FieldInitializer<Color, String> {
    private final Skin skin;

    public SkinColorInitializer(Skin skin) {
        this.skin = skin;
    }
    @Override
    public Color getInitBy(String config) {
        return new Color(skin.getColor(config));
    }
}
