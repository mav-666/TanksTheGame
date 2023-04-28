package com.game.code.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.game.code.utils.Loading.LoadingTask;

public abstract class UILoadingScreen extends LoadingScreen implements UIScreen {
    private final Application app;
    private final UIScreen uiScreen;

    protected UILoadingScreen(Application application, Screen originalScreen, LoadingTask loadingTask) {
        super(application, originalScreen, loadingTask);
        this.app = application;

        this.uiScreen = new UIScreenImpl(application) {
            @Override
            protected void initStage() {
                UILoadingScreen.this.initStage();
            }
        };
    }

    protected abstract void initStage();

    @Override
    public void render(float delta) {
        super.render(delta);

        uiScreen.render(delta);
    }

    @Override
    public Skin getSkin() {
        return uiScreen.getSkin();
    }

    @Override
    public Stage getStage() {
        return uiScreen.getStage();
    }

    @Override
    public LoadingTask getLoadingTask() {
        return uiScreen.getLoadingTask();
    }

    @Override
    public LoadingScreens getLoadingScreenType() {
        return uiScreen.getLoadingScreenType();
    }
}
