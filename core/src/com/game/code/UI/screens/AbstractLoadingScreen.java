package com.game.code.UI.screens;

import com.game.code.Application;
import com.game.code.UI.screens.Loading.LoadableScreen;
import com.game.code.UI.screens.Loading.LoadingException;
import com.game.code.UI.screens.Loading.TaskLoader;

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
        if(loadingTask.isDone()) {
            loadableScreen.loaded();
            app.setScreen(loadableScreen);
        }

        try {
            loadTask();
        } catch (LoadingException e) {
            resolveError(e);
        }
    }

    abstract protected void loadTask() throws LoadingException;

    abstract protected void resolveError(LoadingException loadingException);

}
