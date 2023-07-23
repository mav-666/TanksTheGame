package com.game.code.screens.Loading;

import com.game.code.Application;
import com.game.code.screens.LoadingBarScreen;
import com.game.code.screens.AbstractLoadingScreen;

public class LoadingScreenFactory {


    private final Application app;

    public LoadingScreenFactory(Application app) {
        this.app = app;
    }

    public AbstractLoadingScreen get(LoadableScreen loadableScreen) {
        return switch (loadableScreen.getLoadingScreenType()) {
            default -> new LoadingBarScreen(app, loadableScreen, loadableScreen.getLoadingTask());

        };
    }
}