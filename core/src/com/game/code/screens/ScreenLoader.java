package com.game.code.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import java.util.ArrayList;

public class ScreenLoader {
    private final LoadingScreenFactory loadingScreenFactory;
    private final ArrayList<LoadableScreen> screenQueue;
    private final Game game;

    ScreenLoader(Game game, LoadingScreenFactory loadingScreenFactory) {
        this.loadingScreenFactory = loadingScreenFactory;
        this.game = game;

        screenQueue = new ArrayList<>();
    }

    public void loadScreen(Screen screen) {
        if(screen instanceof LoadableScreen loadableScreen) {
            if(screenQueue.contains(loadableScreen)) {
                screenQueue.remove(loadableScreen);
                game.setScreen(screen);
                return;
            }
            screenQueue.add(loadableScreen);
            loadScreen(loadingScreenFactory.get(screen, loadableScreen.getLoadingTask(), loadableScreen.getLoadingScreenType()));
            return;
        }

        game.setScreen(screen);
    }
}
