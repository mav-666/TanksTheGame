package com.game.code.UI;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.ray3k.tenpatch.TenPatchDrawable;

public class LoadingBar extends IndicationBar {
    float loadingTasksAmount;

    public LoadingBar(Skin skin, float loadingTasksAmount) {
        this.loadingTasksAmount = loadingTasksAmount;

        Image foreground = new Image(skin.get("LoadingBar-T", TenPatchDrawable.class));

        slider = new Slider(0, loadingTasksAmount, .01f,false, skin, "loadingBar");

        slider.setValue(0);
        slider.setAnimateInterpolation(Interpolation.circle);
        slider.setAnimateDuration(.2f);

        addActor(new Container<>(slider).prefSize(getWidth() - 2, getHeight()).pad(0,2, 0, 2));
        addActor(new Container<>(foreground).width(getWidth()));
    }
}
