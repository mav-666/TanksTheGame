package com.game.code.UI.screens.Loading;

import com.badlogic.gdx.Screen;
import com.game.code.Application;

public class ScreenLoader {
    private final LoadingScreenFactory loadingScreenFactory;
    private final Application app;

    public ScreenLoader(Application app) {
        this.loadingScreenFactory = new LoadingScreenFactory(app);
        this.app = app;
    }

    public void loadScreen(Screen screen) {
        if(screen instanceof LoadableScreen loadableScreen) {
            setLoadingScreen(loadableScreen);
            return;
        }

        app.setScreen(screen);
    }

    private void setLoadingScreen(LoadableScreen loadableScreen) {
        app.setScreen(loadingScreenFactory.get(loadableScreen));
    }
}
