package com.game.code.screens.Loading;

import com.badlogic.gdx.Screen;
import com.game.code.Application;

public class ScreenLoader {
    private final LoadingScreenFactory loadingScreenFactory;
    private final Application app;

    private Screen nextScreen;

    public ScreenLoader(Application app) {
        this.loadingScreenFactory = new LoadingScreenFactory(app);
        this.app = app;
    }

    public void updateScreen() {
        if(nextScreen == null) return;
        if(nextScreen instanceof LoadableScreen loadableScreen) {
            nextScreen = null;
            setLoadingScreen(loadableScreen);
            return;
        }

        app.setScreen(nextScreen);
        nextScreen = null;

    }

    public void loadScreen(Screen screen) {
            this.nextScreen = screen;
    }

    private void setLoadingScreen(LoadableScreen loadableScreen) {
        app.setScreen(loadingScreenFactory.get(loadableScreen));
    }
}
