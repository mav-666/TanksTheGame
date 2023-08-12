package com.game.code.screens.Loading;

import com.badlogic.gdx.Screen;

public interface LoadableScreen extends Screen {

    TaskLoader getLoadingTask();
}