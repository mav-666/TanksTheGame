package com.game.code.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.game.code.systems.RenderingSystem;

public class HealthMeter extends Meter {

    private static final float SCALE = 1/4f;

    private final Image blendingMask;
    private final Image front;

    public HealthMeter(Skin skin, MeterConfig meterConfig) {
        Drawable frontD = skin.getDrawable("healthMeter");
        float width = frontD.getMinWidth() * SCALE;
        float height = frontD.getMinHeight() * SCALE;

        this.setScale(RenderingSystem.toMeters(0.5f));

        setSize(RenderingSystem.toMeters(width), RenderingSystem.toMeters(1f));

        initSlider(skin, width * 2, height * 2, meterConfig);
        addActor(slider);

        blendingMask = new Image(skin.getDrawable("healthMeterBlendingMask"));
        blendingMask.setScale(SCALE * 2);
        addActor(blendingMask);

        front = new Image(frontD);
        front.setScale(SCALE * 2);
        addActor(front);
    }

    @Override
    protected void initSlider(Skin skin, float width, float height, MeterConfig meterConfig) {
        Slider.SliderStyle sliderStyle = createSliderStyle(skin, width);

        slider = new Slider(meterConfig.min(), meterConfig.max(), meterConfig.step(), true, sliderStyle);
        slider.setAnimateDuration(2f);
        slider.setAnimateInterpolation(Interpolation.swingOut);
        slider.setHeight(height - 3);
    }

    private Slider.SliderStyle createSliderStyle(Skin skin, float width) {
        Slider.SliderStyle sliderStyle = skin.get("health", Slider.SliderStyle.class);
        sliderStyle.background.setMinWidth(width);
        sliderStyle.knob.setMinWidth(width);
        sliderStyle.knobBefore.setMinWidth(width);

        return sliderStyle;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        validate();
        applyTransform(batch, computeTransform());

        drawMask(batch, parentAlpha);

        drawMaskedSlider(batch, parentAlpha);

        front.draw(batch, parentAlpha);

        resetTransform(batch);
    }

    private void drawMask(Batch batch, float parentAlpha) {
        Gdx.gl.glColorMask(false, false, false, true);
        batch.setBlendFunction(GL30.GL_ONE, GL30.GL_ZERO);

        blendingMask.draw(batch, parentAlpha);

        batch.setBlendFunction(GL30.GL_ZERO, GL30.GL_SRC_ALPHA);

        slider.draw(batch, parentAlpha);
    }

    private void drawMaskedSlider(Batch batch, float parentAlpha) {
        Gdx.gl.glColorMask(true, true, true, true);

        batch.setBlendFunction(GL30.GL_DST_ALPHA, GL30.GL_ONE_MINUS_DST_ALPHA);

        slider.draw(batch, parentAlpha);

        batch.setBlendFunction(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
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
