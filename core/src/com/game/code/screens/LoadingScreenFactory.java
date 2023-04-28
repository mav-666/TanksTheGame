package com.game.code.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Null;
import com.game.code.utils.Loading.LoadingTask;

public class LoadingScreenFactory {
    private final Application application;

    LoadingScreenFactory(Application application) {
        this.application = application;
    }

    public LoadingScreen get(Screen originalScreen, LoadingTask loadingTask, @Null LoadingScreens type) {
        return switch (type) {
            case loadingBar -> new LoadingBarScreen(application, originalScreen, loadingTask);

            default -> new BlankLoadingScreen(application, originalScreen, loadingTask);
        };
    }
}

