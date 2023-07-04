package com.game.code.UI.screens.Loading;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Null;
import com.game.code.Application;
import com.game.code.UI.screens.LoadingBarScreen;
import com.game.code.UI.screens.AbstractLoadingScreen;

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