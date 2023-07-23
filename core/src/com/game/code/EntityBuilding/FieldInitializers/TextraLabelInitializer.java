package com.game.code.EntityBuilding.FieldInitializers;

import com.badlogic.gdx.utils.Align;
import com.game.code.utils.Assets.Assets;
import com.github.tommyettinger.textra.TextraLabel;

public class TextraLabelInitializer extends ArrayFieldInitializer<TextraLabel, String> {

    private final Assets assets;

    public TextraLabelInitializer(Assets assets) {
        this.assets = assets;
    }

    @Override
    public TextraLabel getInitBy( String[] config) {
        TextraLabel textraLabel = new TextraLabel("", assets.getFont(config[0], Float.parseFloat(config[1])));
        textraLabel.useIntegerPositions(false);
        textraLabel.setAlignment(Align.center);
        return textraLabel;
    }
}
