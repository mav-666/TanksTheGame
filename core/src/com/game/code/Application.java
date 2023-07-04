package com.game.code;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.game.code.UI.screens.AbstractLoadingScreen;
import com.game.code.UI.screens.Loading.ScreenHistory;
import com.game.code.UI.screens.Loading.ScreenLoader;
import com.game.code.utils.Assets;

public class Application extends Game {
    public SpriteBatch batch;
    public Skin skin;
    public Assets assets;

    private ScreenHistory screenHistory;
    private ScreenLoader screenLoader;

    @Override
    public void create() {
        batch = new SpriteBatch();

        screenHistory = new ScreenHistory(5);
        screenHistory.addFilter((screen) -> screen instanceof AbstractLoadingScreen);

        screenLoader = new ScreenLoader(this);

        assets = new Assets();

        assets.loadUISkin();

        skin = assets.getSkin();

        loadScreen(new PlayerMovementTests(this));
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);

        screenHistory.add(screen);
    }

    @Override
    public void dispose(){
        assets.dispose();
        getScreen().dispose();
        batch.dispose();
    }

    public void loadScreen(Screen screen) {
        screenLoader.loadScreen(screen);
    }

    public Screen getPreviousScreen() {
        return screenHistory.getPreviousScreen();
    }
}

