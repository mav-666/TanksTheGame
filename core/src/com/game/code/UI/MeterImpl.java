package com.game.code.UI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.game.code.systems.RenderingSystem;

public class MeterImpl extends Meter {

    public MeterImpl(Skin skin, float width, float height, float maxValue) {

        width = RenderingSystem.toPixels(width);
        height = RenderingSystem.toPixels(height);

        initSlider(skin, width * 2, height * 2, maxValue);

        setSize(RenderingSystem.toMeters(width), RenderingSystem.toMeters(height));

        addActor(slider);

        this.setScale(RenderingSystem.toMeters(0.5f));
    }

    @Override
    protected void initSlider(Skin skin, float width, float height, float maxValue) {
        Slider.SliderStyle sliderStyle = createSliderStyle(skin, width);

        slider = new Slider(0, maxValue,  1f, true, sliderStyle);
        slider.setAnimateDuration(0.5f);
        slider.setAnimateInterpolation(Interpolation.linear);
        slider.setHeight(height);
    }

    private Slider.SliderStyle createSliderStyle(Skin skin, float width) {
        Slider.SliderStyle sliderStyle = skin.get(Slider.SliderStyle.class);
        sliderStyle.background.setMinWidth(width);
        sliderStyle.knob.setMinWidth(width);
        //noinspection SuspiciousNameCombination
        sliderStyle.knob.setMinHeight(width);
        sliderStyle.knobBefore.setMinWidth(width);

        return sliderStyle;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        validate();
        applyTransform(batch, computeTransform());

        slider.draw(batch, parentAlpha);

        resetTransform(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        slider.act(delta);
    }


    public void setValue(float value) {
        slider.setValue(value);
    }
}
