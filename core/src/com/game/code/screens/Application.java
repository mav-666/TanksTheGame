package com.game.code.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.code.AssetManagment.AssetProcessor;

public interface Application {

    void loadScreen(Screen screen);

    Screen getPreviousScreen();

    SpriteBatch getBatch();

    AssetProcessor getAssetProcessor();
}
