package com.game.code.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.game.code.AssetManagment.AssetProcessor;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.utils.Loading.AssetTaskLoader;
import com.game.code.utils.Loading.LoadingTask;
import com.game.code.utils.Loading.MultipleTaskLoader;
import com.game.code.utils.Loading.SingleTaskLoader;

public abstract class UIScreenImpl implements UIScreen, AssetRequest {
    protected final Application app;

    private final Stage stage;

    private Skin skin;
    protected UIScreenImpl(Application application) {
        this.app = application;

        stage = new Stage(new FillViewport(840, 480), app.getBatch());

        request(application.getAssetProcessor());
        app.getAssetProcessor().load();
    }

    @Override
    public void request(AssetProcessor assetRequestProcessor) {
        assetRequestProcessor.receiveRequest("skin.json", Skin.class, this);
    }

    @Override
    public void passAssets(AssetProcessor assets) {
        skin = assets.get("skin.json", Skin.class);
    }

    protected abstract void initStage();

    @Override
    public void show() {
        stage.addAction(Actions.sequence(Actions.fadeIn(0.1f),
                Actions.run(() -> Gdx.input.setInputProcessor(stage))));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
        stage.getCamera().update();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        stage.getRoot().clearActions();
        stage.unfocusAll();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public LoadingTask getLoadingTask() {
        return new MultipleTaskLoader(
                new AssetTaskLoader(app.getAssetProcessor()),
                new SingleTaskLoader(this::initStage, "initStage"));
    }

    @Override
    public LoadingScreens getLoadingScreenType() {
        return LoadingScreens.blank;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    public Skin getSkin() {
        return skin;
    }
}
