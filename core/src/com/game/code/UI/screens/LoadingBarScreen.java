package com.game.code.UI.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.game.code.Application;
import com.game.code.UI.LoadingBar;
import com.game.code.UI.screens.Loading.LoadableScreen;
import com.game.code.UI.screens.Loading.LoadingException;
import com.game.code.UI.screens.Loading.TaskLoader;

public class LoadingBarScreen extends AbstractLoadingScreen {
    private final LoadingBar loadingBar;
    private final VerticalGroup loadingAlerts;

    public LoadingBarScreen(Application app, LoadableScreen originalScreen, TaskLoader loadingTask) {
        super(app, originalScreen, loadingTask);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        loadingBar = new LoadingBar(app.skin, 1);

        table.add(loadingBar).height(10).center();

        loadingAlerts = new VerticalGroup();

        table.row();
        table.add(loadingAlerts).padTop(5);

        table.setTransform(true);
        table.setOrigin(stage.getWidth()/2, stage.getHeight()/2);
        table.scaleBy(3);
    }

    @Override
    protected void loadTask() throws LoadingException {
        loadingTask.update();

        var slider = loadingBar.getSlider();
        slider.setValue(loadingTask.getProgress());
    }

    @Override
    protected void resolveError(LoadingException loadingException) {
        createAlert(loadingException.toString(), app.skin.getColor("red"));
    }

    private void createAlert(String alert, Color textColor) {
        Label label = new Label(alert, app.skin, "small");
        label.setColor(textColor);

        label.addAction(Actions.sequence(Actions.delay(0.4f), Actions.fadeOut(0.4f), Actions.removeActor()));
        label.setFontScale(0.4f);

        loadingAlerts.addActor(label);
    }
}
