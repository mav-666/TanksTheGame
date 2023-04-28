package com.game.code.screens;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

public class LoginScreen extends UIScreenImpl {

    protected LoginScreen(Application application) {
        super(application);
    }

    @Override
    protected void initStage() {
        Stack tableStack = new Stack();
        tableStack.setFillParent(true);
        getStage().addActor(tableStack);

        tableStack.add(new Container<>(new Image(getSkin(), "TitleBg")).fill());

        Table table = new Table();
        table.setFillParent(true);
        tableStack.add(table);

        Label insertText = new Label("Enter your nickName", getSkin(), "big");
        insertText.setColor(getSkin().getColor("darkPurple"));

        table.add(insertText);

        table.row().spaceTop(50);

        TextField textField = new TextField("", getSkin());
        textField.setAlignment(Align.center);
        textField.setMessageText("your nickName");
        textField.setTextFieldListener((field, c) -> {
            insertText.setText(textField.getText());
        });

        textField.setTextFieldFilter((field, c) -> field.getText().length() < 14);
        
        

        table.add(textField).size(getStage().getWidth()/2, getStage().getHeight()/8);

    }
}
