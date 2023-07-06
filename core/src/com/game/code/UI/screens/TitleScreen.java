package com.game.code.UI.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.game.code.Application;
import com.game.code.GameScreen;


public class TitleScreen extends AbstractUIScreen {

    public TitleScreen(Application app) {
        super(app);

        Stack tableStack = new Stack();
        tableStack.setFillParent(true);
        stage.addActor(tableStack);

        tableStack.add(new Container<>(new Image(app.skin, "TitleBg")).fill());

        Table table = new Table();
        table.setFillParent(true);
        tableStack.add(table);

        table.add(new Image(app.skin.get("TitleText", Drawable.class)))
                .size(stage.getWidth()/1.5f, stage.getHeight()/3).padBottom(stage.getHeight()/8);

        table.row();

        table.add(createPlayButton())
                .size(stage.getViewport().getWorldWidth()/4, stage.getViewport().getWorldHeight()/7).fill();

        table.row();

        table.add(createLeaveButton())
                .size(stage.getViewport().getWorldWidth()/6f, stage.getViewport().getWorldHeight()/9f).fill().padTop(10);

    }

    private Button createPlayButton() {
        TextButton playBtn= new TextButton("PLAY", app.skin, "play");

        playBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                stage.addAction(Actions.sequence(Actions.fadeOut(0.2f),
                        Actions.run(() -> app.loadScreen(new GameScreen(app)))));

            }
        });

        return playBtn;
    }

    private Button createLeaveButton() {
        TextButton leaveBtn = new TextButton("leave", app.skin);
        leaveBtn.getLabel().setFontScale(1.2f);

        leaveBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }
        });

        return leaveBtn;
    }

    @Override
    public void show() {
        stage.addAction(Actions.fadeIn(0.1f));
        super.show();
    }
}
