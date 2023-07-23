package com.game.code.screens.Loading;

import com.badlogic.gdx.Screen;

public interface LoadableScreen extends Screen {

    default void loaded() {}

    TaskLoader getLoadingTask();

    default LoadingScreen getLoadingScreenType() {
        return LoadingScreen.loadingBar;
    }
}