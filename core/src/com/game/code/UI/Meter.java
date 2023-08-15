package com.game.code.UI;

import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

public abstract class Meter extends WidgetGroup {

    private final Signal<Float> signal = new Signal<>();

    protected Slider slider;

    abstract protected void initSlider(Skin skin, float width, float height, MeterConfig meterConfig);

    public float getValue() {
        return slider.getValue();
    }

    public void addValue(float value) {
        this.setValue(getValue() + value);
    }

    public void setValue(float value) {
        slider.setValue(value);
        signal.dispatch(slider.getValue());
    }

    public Signal<Float> getSignal() {
        return signal;
    }

    public static record MeterConfig(
            float min,
            float max,
            float step
    ) {
        public MeterConfig(float max) {
            this(max, 1);
        }
        public MeterConfig(float max, float step) {
            this(0, max, step);
        }
    }
}
