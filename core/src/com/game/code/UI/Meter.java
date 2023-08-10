package com.game.code.UI;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

public abstract class Meter extends WidgetGroup {

    protected Slider slider;

    abstract protected void initSlider(Skin skin, float width, float height, float maxValue);

    public float getValue() {
        return slider.getValue();
    }

    public void addValue(float value) {
        slider.setValue(getValue() + value);
    }

    public void setValue(float value) {
        slider.setValue(value);
    }
}
