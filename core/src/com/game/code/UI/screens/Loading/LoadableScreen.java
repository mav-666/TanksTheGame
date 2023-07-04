package com.game.code.UI.screens.Loading;

import com.badlogic.gdx.Screen;

public interface LoadableScreen extends Screen {

    void loaded();

    TaskLoader getLoadingTask();

    default LoadingScreen getLoadingScreenType() {
        return LoadingScreen.loadingBar;
    }
}