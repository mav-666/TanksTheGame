package com.game.code.screens.Loading;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import com.game.code.Application;
import com.game.code.screens.AbstractLoadingScreen;
import com.game.code.screens.LoadingBarScreen;

public class ScreenLoader {
    private final Application app;

    private final Array<Screen> loadedScreens = new Array<>();

    private Screen nextScreen;

    public ScreenLoader(Application app) {
        this.app = app;
    }

    public void updateScreen() {
        if(nextScreen == null) return;
        if(nextScreen instanceof LoadableScreen loadableScreen && !loadedScreens.contains(loadableScreen, false)) {
            nextScreen = null;
            loadedScreens.add(loadableScreen);
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
        app.setScreen(createLoadingScreen(loadableScreen, loadableScreen.getLoadingTask()));
    }

    private AbstractLoadingScreen createLoadingScreen(LoadableScreen loadableScreen, TaskLoader taskLoader) {
        return new LoadingBarScreen(app, loadableScreen, taskLoader);
    }

    public void disposeScreen(Screen screen) {
        loadedScreens.removeValue(screen, false);
        screen.dispose();
    }
}
