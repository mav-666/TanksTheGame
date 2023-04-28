package com.game.code.screens;

import com.badlogic.gdx.Screen;
import com.game.code.utils.Loading.LoadingException;
import com.game.code.utils.Loading.LoadingTask;

public abstract class LoadingScreen implements Screen {
    private final Application application;
    private final Screen originalScreen;

    protected final LoadingTask loadingTask;

    protected LoadingScreen(Application application, Screen originalScreen, LoadingTask loadingTask) {
        this.application = application;
        this.originalScreen = originalScreen;
        this.loadingTask = loadingTask;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if(loadingTask.isDone()) application.loadScreen(originalScreen);

        try {
            loadTask();
        } catch (LoadingException e) {
            resolveError(e);
        }
    }

    abstract protected void loadTask() throws LoadingException;

    abstract protected void resolveError(LoadingException loadingException);


    @Override
    public void resize(int width, int height) {
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
