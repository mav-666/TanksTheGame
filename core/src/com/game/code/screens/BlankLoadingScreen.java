package com.game.code.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.game.code.utils.Loading.LoadingException;
import com.game.code.utils.Loading.LoadingTask;

public class BlankLoadingScreen extends LoadingScreen {

    protected BlankLoadingScreen(Application application, Screen originalScreen, LoadingTask loadingTask) {
        super(application, originalScreen, loadingTask);
    }

    @Override
    protected void loadTask() throws LoadingException {
        loadingTask.update();
    }

    @Override
    protected void resolveError(LoadingException loadingException) {
        Gdx.app.log("LOADING ERROR", loadingException.toString());
    }
}
