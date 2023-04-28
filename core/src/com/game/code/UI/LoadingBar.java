package com.game.code.UI;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.ray3k.tenpatch.TenPatchDrawable;

public class LoadingBar extends IndicationBar {
    float loadingTasksAmount;

    public LoadingBar(float loadingTasksAmount) {
        this.loadingTasksAmount = loadingTasksAmount;
    }

    @Override
    public void init(Skin skin) {
        Image foreground = new Image(skin.get("LoadingBar-T", TenPatchDrawable.class));

        slider = new Slider(0, loadingTasksAmount, 1,false, skin, "loadingBar");

        slider.setValue(0);
        slider.setAnimateInterpolation(Interpolation.circle);
        slider.setAnimateDuration(0.2f);

        addActor(new Container<>(slider).prefSize(getWidth() - 2, getHeight()).pad(0,2, 0, 2));
        addActor(new Container<>(foreground).width(getWidth()));
    }


}
