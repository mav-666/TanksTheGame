package com.game.code.UI;

import com.badlogic.gdx.scenes.scene2d.ui.*;

public abstract class IndicationBar extends Stack {
    protected float lastValue;

    protected Slider slider;
    protected Image foreground;

    public abstract void init(Skin skin);

    public Slider getSlider() {
        return slider;
    }
}
