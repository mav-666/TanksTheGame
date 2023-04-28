package com.game.code.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.game.code.utils.Loading.LoadingTask;

public class ConnectionChoiceScreen extends UIScreenImpl implements LoadableScreen {

    public ConnectionChoiceScreen(Application application) {
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

        table.add(createChoicePane()).fill();

        tableStack.add(new Container<>(createBackButton()).size(getStage().getWidth()/20f).right().top().pad(20,0, 0, 20));
    }

    private Button createBackButton() {
        TextButton backBtn = new TextButton("back", getSkin());
        backBtn.getLabel().setFontScale(.8f);

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

    private HorizontalGroup createChoicePane() {
        HorizontalGroup choicePane = new HorizontalGroup();

        choicePane.addActor(createHostChoice());

        choicePane.space(80);

        choicePane.addActor(createConnectChoice());

        return choicePane;


    }

    private VerticalGroup createHostChoice() {
        VerticalGroup choice = new VerticalGroup();

        Label hostText = new Label("Establish\nServer", getSkin(), "big");
        hostText.setAlignment(Align.center, Align.center);
        hostText.setColor(getSkin().getColor("darkPurple"));

        choice.addActor( new Container<>(hostText).fill().center());

        Button hostBtn = new Button(getSkin());

        hostBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                getStage().addAction(Actions.sequence(Actions.fadeOut(0.2f),
                        Actions.run(() -> app.loadScreen(new GameScreen(app)))));
            }
        });

        choice.space(20);

        choice.addActor(new Container<>(hostBtn).size(getStage().getWidth()/5f));

        hostBtn.add(new Image(getSkin(), "hostIcon")).grow().pad(60);

        return choice;
    }

    private VerticalGroup createConnectChoice() {
        VerticalGroup choice = new VerticalGroup();

        Label connectText = new Label("Connect\nto the Server", getSkin(), "big");
        connectText.setAlignment(Align.center, Align.center);
        connectText.setColor(getSkin().getColor("darkPurple"));

        choice.addActor( new Container<>(connectText).fill().center());

        Button connectBtn = new Button(getSkin());

        connectBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                getStage().addAction(Actions.sequence(Actions.fadeOut(0.2f),
                        Actions.run(() -> app.loadScreen(new GameScreen(app)))));
            }
        });

        choice.space(20);

        choice.addActor(new Container<>(connectBtn).size(getStage().getWidth()/5f));

        connectBtn.add(new Image(getSkin(), "connectIcon")).grow().pad(60);

        return choice;
    }

    @Override
    public void show() {
        getStage().addAction(Actions.fadeIn(0.1f));
        super.show();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
