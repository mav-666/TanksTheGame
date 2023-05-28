package com.game.code.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.Align;

public class LoginScreen extends UIScreenImpl {
    String userNickname;

    protected LoginScreen(Application application) {
        super(application);
    }

    @Override
    protected void initStage() {
        Stack tableStack = new Stack();
        tableStack.setFillParent(true);

        getStage().addActor(tableStack);

        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(getSkin().getDrawable("TitleBg"));

        Label insertText = new Label("Enter your nickname", getSkin(), "big");
        insertText.setColor(getSkin().getColor("darkPurple"));

        Label yourNickName = new Label("", getSkin(), "big");
        yourNickName.setColor(getSkin().getColor("darkPurple"));

        table.add(insertText);
        table.row().spaceTop(10);
        table.add(yourNickName);

        table.row().spaceTop(50);

        TextField textField = new TextField("", getSkin());
        textField.setAlignment(Align.center);
        textField.setMessageText("your nickname");
        textField.setTextFieldListener((field, c) -> {
            insertText.setText("Your name is:");
            yourNickName.setText(textField.getText());
            userNickname = textField.getText();
        });

        textField.setTextFieldFilter((field, c) -> field.getText().length() < 14);

        textField.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                yourNickName.setColor(getSkin().getColor("darkPurple"));
            }
        });

        textField.addListener(new InputListener() {

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if(keycode == Input.Keys.ENTER) {
                    getStage().setKeyboardFocus(null);
                    yourNickName.setColor(getSkin().getColor("purple"));
                }

                return true;
            }
        });

        table.add(textField).size(getStage().getWidth()/2, getStage().getHeight()/8);

        tableStack.add(new Container<>(createNextButton()).size(getStage().getWidth()/12).align(Align.bottomRight).pad(0, 0, 20, 10));

        tableStack.add(new Container<>(createBackButton()).size(getStage().getWidth()/12).align(Align.bottomLeft).pad(0, 10, 20, 0));
    }

    private Button createBackButton() {
        TextButton backBtn = new TextButton("back", getSkin());

        backBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                getStage().addAction(Actions.sequence(Actions.fadeOut(0.2f),
                        Actions.run(() -> app.loadScreen(app.getPreviousScreen()))));
            }
        });

        return backBtn;
    }

    private Button createNextButton() {
        TextButton toNextBtn = new TextButton("next", getSkin());
        toNextBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(userNickname == null || userNickname.equals("")) return;
                getStage().addAction(Actions.sequence(Actions.fadeOut(0.2f),
                        Actions.run(() -> app.loadScreen(new ConnectionChoiceScreen(app)))));
            }
        });

        return toNextBtn;
    }
}
