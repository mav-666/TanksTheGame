package com.game.code.screens;

import com.game.code.Application;
import com.game.code.screens.Loading.LoadableScreen;
import com.game.code.screens.Loading.LoadingException;
import com.game.code.screens.Loading.TaskLoader;

public abstract class AbstractLoadingScreen extends AbstractUIScreen {
    private final LoadableScreen  loadableScreen;

    protected final TaskLoader loadingTask;

    protected AbstractLoadingScreen(Application app, LoadableScreen loadableScreen, TaskLoader loadingTask) {
        super(app);

        this.loadableScreen = loadableScreen;
        this.loadingTask = loadingTask;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if(loadingTask.isDone()) {
            finishLoading();
            return;
        }

        try {
            loadTask();
        } catch (LoadingException e) {
            resolveError(e);
        }


    }

    protected void finishLoading() {
        loadableScreen.loaded();
        app.setScreen(loadableScreen);
    }

    abstract protected void loadTask() throws LoadingException;

    abstract protected void resolveError(LoadingException loadingException);

}
