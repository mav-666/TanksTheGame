package com.game.code.UI.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.game.code.Application;
import com.game.code.UI.screens.Loading.LoadableScreen;

public abstract class AbstractUIScreen extends AbstractScreen {

    protected Stage stage;

    public AbstractUIScreen(Application app) {
        super(app);

        stage = new Stage(new ExtendViewport(840, 480), app.batch);
    }

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
}
