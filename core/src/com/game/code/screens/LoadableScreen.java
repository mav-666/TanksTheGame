package com.game.code.screens;

import com.badlogic.gdx.Screen;
import com.game.code.utils.Loading.LoadingTask;

public interface LoadableScreen extends Screen {

    LoadingTask getLoadingTask();

    LoadingScreens getLoadingScreenType();
}
