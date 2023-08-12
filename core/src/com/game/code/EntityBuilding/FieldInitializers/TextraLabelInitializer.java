package com.game.code.EntityBuilding.FieldInitializers;

import com.badlogic.gdx.utils.Align;
import com.game.code.utils.Assets.Assets;
import com.github.tommyettinger.textra.TypingLabel;

public class TextraLabelInitializer extends ArrayFieldInitializer<TypingLabel, String> {

    private final Assets assets;

    public TextraLabelInitializer(Assets assets) {
        this.assets = assets;
    }

    @Override
    public TypingLabel getInitBy( String[] config) {
        TypingLabel TypingLabel = new TypingLabel("", assets.getFont(config[0], Float.parseFloat(config[1])));
        TypingLabel.useIntegerPositions(false);
        TypingLabel.setAlignment(Align.center);
        return TypingLabel;
    }
}
