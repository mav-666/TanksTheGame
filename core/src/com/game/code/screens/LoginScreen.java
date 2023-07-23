package com.game.code.screens;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.game.code.Application;

public class LoginScreen extends AbstractUIScreen {

    protected LoginScreen(Application app) {
        super(app);

        Stack tableStack = new Stack();
        tableStack.setFillParent(true);
        stage.addActor(tableStack);

        tableStack.add(new Container<>(new Image(app.skin, "TitleBg")).fill());

        Table table = new Table();
        table.setFillParent(true);
        tableStack.add(table);

        Label insertText = new Label("Enter your nickName", app.skin, "big");
        insertText.setColor(app.skin.getColor("darkPurple"));

        table.add(insertText);

        table.row().spaceTop(50);

        TextField textField = new TextField("", app.skin);
        textField.setAlignment(Align.center);
        textField.setMessageText("your nickName");
        textField.setTextFieldListener((field, c) -> {
            insertText.setText(textField.getText());
        });

        textField.setTextFieldFilter((field, c) -> field.getText().length() < 14);



        table.add(textField).size(stage.getWidth()/2, stage.getHeight()/8);

    }
}
