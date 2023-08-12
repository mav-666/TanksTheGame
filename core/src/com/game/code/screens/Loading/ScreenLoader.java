package com.game.code.screens.Loading;

import com.badlogic.gdx.Screen;
import com.game.code.Application;
import com.game.code.screens.AbstractLoadingScreen;
import com.game.code.screens.LoadingBarScreen;

public class ScreenLoader {
    private final Application app;
    private final ScreenHistory screenHistory;

    private Screen nextScreen;

    public ScreenLoader(Application app, ScreenHistory screenHistory) {
        this.app = app;
        this.screenHistory = screenHistory;
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
        TaskLoader taskLoader = screenHistory.containsScreen(loadableScreen) ? TaskLoader.create().get() : loadableScreen.getLoadingTask();
        app.setScreen(createLoadingScreen(loadableScreen, taskLoader));
    }

    private AbstractLoadingScreen createLoadingScreen(LoadableScreen loadableScreen, TaskLoader taskLoader) {
        return new LoadingBarScreen(app, loadableScreen, taskLoader);
    }
}
