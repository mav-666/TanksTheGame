package com.game.code.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.game.code.Application;

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
        super.render(delta);
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
