package com.game.code.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.game.code.BattleField.ContentParameter;
import com.game.code.BattleField.PercentParameter;
import com.game.code.screens.Connection.*;
import com.game.code.utils.Loading.LoadingTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class HostScreen extends UIScreenImpl {
    ContentSetterFactory contentFactory;
    HashMap<ObstacleBuilders, BattleFieldContentSetter> chosenContentSetters;

    protected HostScreen(Application application) {
        super(application);
        contentFactory = new ContentSetterFactory();
        chosenContentSetters = new HashMap<>();
    }

    @Override
    protected void initStage() {
        Table table = new Table();
        table.setFillParent(true);

        table.setBackground(getSkin().getDrawable("TitleBg"));

        getStage().addActor(table);

        Label settingsText = new Label("Настройки Сервера", getSkin(), "big");
        settingsText.setColor(getSkin().getColor("darkPurple"));
        settingsText.setAlignment(Align.center, Align.center);

        table.add(settingsText).size(10, 20).spaceBottom(20).colspan(2);

        table.row();

        table.add(createSettings()).size(400, 300).spaceRight(20);

        table.add(createSettings()).size(400, 300);

        table.row();

        table.add(createSubmitButton()).colspan(2).size(100, 50);


    }

    private Actor createSettings() {
        Table settings = new Table();
        settings.top();

        settings.defaults().space(10).growX();

        settings.add(createSetting(createSettingField(createFieldName("Размер"),
                applyIndicator(createSettingSlider(10, 100, 1)))));

        settings.row();
        settings.add(createSetting(createSettingField(createFieldName("Cид"),
                createSettingTextField(String.valueOf(new Random().nextInt(100000)),
                        (field, c) -> Character.isDigit(c) & field.getText().length() <= 10))));

        for(ObstacleBuilders obstacleBuilders : ObstacleBuilders.values()) {
            settings.row();
            settings.add(createAddContentSetting(obstacleBuilders));
        }

        return applyScroll(settings);
    }

    private Actor applyScroll(Actor widget) {
        ScrollPane scrollPane = new ScrollPane(widget, getSkin());
        scrollPane.setFadeScrollBars(false);
        scrollPane.setOverscroll(false, false);

        return scrollPane;
    }


    private Actor createSetting(Actor fields) {
        Stack setting = new Stack();
        setting.add(new Container<>(new Image(getSkin(), "settingBack-T")).fill());

        setting.add(fields);

        return setting;
    }

    private Actor createFieldsGroup(Actor... settingFields) {
        Table fields = new Table();
        for (Actor field : settingFields) {
            fields.add(field).expand().left();
            fields.row();
        }

        return fields;
    }

    private Actor createSettingField(Actor... settingWidgets) {
        Table field = new Table();
        //field.debug();
        for(int i = 0; i < settingWidgets.length-1; i++) {
            field.add(settingWidgets[i]).grow().spaceRight(10);
        }

        field.add(settingWidgets[settingWidgets.length-1]).grow();
        field.pad(10);

        return field;
    }

    private Actor createFieldName(String name) {
        Label fieldName = new Label(name + " :", getSkin());
        fieldName.setColor(getSkin().getColor("lightBrown"));
        return fieldName;
    }

    private Slider createSettingSlider(float min, float max, float step) {
        return new Slider(min,max,step, false, getSkin(), "setting");
    }

    private Actor applyIndicator(Slider slider) {
        Table indicatedSlider = new Table();

        indicatedSlider.add(slider).space(10);

        Label indicator = new Label(String.valueOf((int)slider.getValue()), getSkin());
        indicator.setColor(getSkin().getColor("lightBrown"));
        indicatedSlider.right().add(indicator).width(40);

        slider.addListener((event) -> {
            indicator.setText(String.valueOf((int)slider.getValue()));
            return true;});

        return indicatedSlider;
    }

    private Actor createSettingTextField( String text, TextField.TextFieldFilter filter) {
        TextField textField = new TextField(text, getSkin(), "setting");
        textField.setAlignment(Align.center);
        textField.setTextFieldFilter(filter);
        textField.addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.ENTER)
                    getStage().setKeyboardFocus(null);

                return true;
            }
        });

        return new Container<>(textField).fill();
    }

    private Actor createAddContentSetting(ObstacleBuilders obstacle) {
        CheckBox checkBox = new CheckBox("", getSkin(), "setting");
        Table fields = new Table();
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(checkBox.isChecked()) {
                    BattleFieldContentSetter setter = contentFactory.createContentSetter(obstacle);
                    chosenContentSetters.put(obstacle, setter);
                    fields.add(createContentFields(setter));
                } else {
                    chosenContentSetters.remove(obstacle);
                    fields.clear();
                }
            }
        });
        return createSetting(createFieldsGroup(createSettingField(createFieldNameByContent(obstacle), checkBox), fields));
    }

    private Actor createFieldNameByContent(ObstacleBuilders obstacle) {
        return createFieldName(
                switch (obstacle) {
                    case grass -> "Кусты";
                    case box -> "Коробки";
                    case gasoline -> "Бочки";
                });
    }

    private Actor createContentFields(BattleFieldContentSetter contentSetter) {
        ArrayList<Actor> fields = new ArrayList<>();
        contentSetter.getParameters().forEach((parameter) ->
            fields.add(createFieldByParameter(parameter)));

        return createFieldsGroup(fields.toArray(new Actor[]{}));
    }

    private Actor createFieldByParameter(ContentParameter<?> parameter) {
        if(parameter instanceof PercentParameter percentParameter) {
            Slider slider = createSettingSlider(0, 100, 1);
            slider.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    percentParameter.setValue(slider.getValue());
                    System.out.println(slider.getValue());
                    System.out.println(percentParameter.getValue());
                }
            });
            return createSettingField(createFieldName("Плотность"), slider);
        }
        return null;
    }

    private Actor createSubmitButton() {
        TextButton submitButton = new TextButton("Играть", getSkin(), "play");

        submitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                chosenContentSetters.values().forEach(
                        (setter) -> app.getConnectionFactory().getBattleFieldSetter().addContent(setter)
                );
                app.loadScreen(new GameScreen(app));
            }
        });

        return submitButton;
    }

    private Actor createPlayerList() {
        return null;
    }

    @Override
    public LoadingTask getLoadingTask() {
        return super.getLoadingTask();
    }
}
