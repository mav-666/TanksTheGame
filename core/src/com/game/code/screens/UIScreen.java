package com.game.code.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public interface UIScreen extends LoadableScreen {
    Stage getStage();

    Skin getSkin();
}
