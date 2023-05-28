package com.game.code.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;


public class TitleScreen extends UIScreenImpl {

    protected TitleScreen(Application application) {
        super(application);
    }

    @Override
    protected void initStage() {
        Table table = new Table();
        table.setFillParent(true);

        table.setBackground("TitleBg");

        table.setDebug(true);

        getStage().addActor(table);

        table.add(new Image(getSkin().get("TitleText", Drawable.class)))
                .size(getStage().getWidth()/1.5f, getStage().getHeight()/3).padBottom(getStage().getHeight()/8);

        table.row();

        table.add(createPlayButton())
                .size(getStage().getViewport().getWorldWidth()/4, getStage().getViewport().getWorldHeight()/7).fill();

        table.row();

        table.add(createLeaveButton())
                .size(getStage().getViewport().getWorldWidth()/6f, getStage().getViewport().getWorldHeight()/9f).fill().padTop(10);
    }

    private Button createPlayButton() {
        TextButton playBtn= new TextButton("PLAY", getSkin(), "play");
        playBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                getStage().addAction(Actions.sequence(Actions.fadeOut(0.2f),
                        Actions.run(() -> app.loadScreen(new LoginScreen(app)))));

            }
        });

        return playBtn;
    }

    private Button createLeaveButton() {
        TextButton leaveBtn = new TextButton("leave", getSkin());
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
        getStage().addAction(Actions.fadeIn(0.1f));
        super.show();
    }
}
