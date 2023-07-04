package com.game.code.UI.screens;

import com.badlogic.gdx.Screen;
import com.game.code.Application;

public abstract class AbstractScreen implements Screen {

    protected Application app;

    public AbstractScreen(Application app) {
        this.app = app;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
