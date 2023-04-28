package com.game.code.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.game.code.UI.LoadingBar;
import com.game.code.utils.Loading.LoadingException;
import com.game.code.utils.Loading.LoadingTask;

public class LoadingBarScreen extends UILoadingScreen {
    private LoadingBar loadingBar;
    private VerticalGroup loadingAlerts;

    protected LoadingBarScreen(Application application, Screen originalScreen, LoadingTask loadingTask) {
        super(application, originalScreen, loadingTask);
    }

    @Override
    protected void initStage() {
        Table table = new Table();
        table.setFillParent(true);
        getStage().addActor(table);

        loadingBar = new LoadingBar(1);
        loadingBar.init(getSkin());

        table.add(loadingBar).height(10).center();

        loadingAlerts = new VerticalGroup();

        table.row();
        table.add(loadingAlerts).padTop(5);

        table.setTransform(true);
        table.setOrigin(getStage().getWidth()/2, getStage().getHeight()/2);
        table.scaleBy(3);
    }

    @Override
    protected void loadTask() throws LoadingException {
        loadingTask.update();

        var slider = loadingBar.getSlider();
        slider.setValue(loadingTask.getPercent());
    }

    @Override
    protected void resolveError(LoadingException loadingException) {
        createAlert(loadingException.toString(), getSkin().getColor("red"));
    }

    private void createAlert(String alert, Color textColor) {
        Label label = new Label(alert, getSkin(), "small");
        label.setColor(textColor);

        label.addAction(Actions.sequence(Actions.delay(0.4f), Actions.fadeOut(0.4f), Actions.removeActor()));
        label.setFontScale(0.4f);

        loadingAlerts.addActor(label);
    }
}
