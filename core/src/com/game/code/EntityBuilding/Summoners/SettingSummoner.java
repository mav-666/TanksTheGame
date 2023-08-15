package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectFloatMap;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.EntityCreator;
import com.game.code.UI.Meter;
import com.game.code.UI.MeterImpl;
import com.game.code.components.*;
import com.github.tommyettinger.textra.TypingLabel;

public class SettingSummoner extends EntityBuilderSummoner {

    private final Skin skin;
    protected final EntityCreator entityCreator;
    protected final Vector2 pos = new Vector2();

    public SettingSummoner(EntityBuilder entityBuilder, Engine engine, Skin skin, EntityCreator entityCreator) {
        super(entityBuilder, engine);

        this.skin = skin;
        this.entityCreator = entityCreator;
    }

    @Override
    public Entity summonBy(Entity summoner) {
        pos.set(mappers.get(TransformComponent.class, summoner).position);

        String fillingName = mappers.get(SummonsNowComponent.class, summoner).entityName;

        SettingTemplateComponent settingTemplateC = mappers.get(SettingTemplateComponent.class, summoner);
        boolean isDisabled = settingTemplateC.isDisabled;
        ObjectFloatMap<String> setting = settingTemplateC.setting;
        String labelText = settingTemplateC.labelText;
        Meter.MeterConfig meterConfig = settingTemplateC.meterConfig;

        createFillingSprite(fillingName);

        Entity meterE = createMeterEntity();

        Meter meter = createMeterWidgetFor(meterConfig);
        mappers.get(WidgetComponent.class, meterE).widget = meter;

        TypingLabel label = createPercentageLabel(labelText);

        meter.getSignal().add((signal, value) -> {
            setting.put(fillingName, value);
            label.setText(labelText.replace("{value}", String.valueOf(value.intValue())));
        });

        meter.setValue(setting.get(fillingName, 0));

        createIncrementButton(meter, isDisabled);

        createDecrementButton(meter, isDisabled);

        entityCreator.setSummonerType(SummonerType.BattlefieldSetting);

        return meterE;
    }

    private void createFillingSprite(String fillingName) {
        entityCreator.clearSettings();
        entityCreator.setSummonerType(SummonerType.Sprite);
        entityCreator.createEntityOn(pos.x, pos.y + 1.5f, 1, fillingName);
    }

    private TypingLabel createPercentageLabel(String labelText) {
        entityBuilder.build("Text");
        TypingLabel label = entityBuilder.getComponent(TextComponent.class).label;
        label.setText(labelText);
        entityBuilder.getComponent(TransformComponent.class).position.set(pos.x, pos.y + 1.5f);
        entityBuilder.getComponent(TransformComponent.class).zIndex = 0.9f;

        engine.addEntity(entityBuilder.getEntity());

        return label;
    }

    private Entity createMeterEntity() {
        entityBuilder.build("Widget");

        entityBuilder.getComponent(TransformComponent.class).position.set(pos);
        entityBuilder.getComponent(TransformComponent.class).zIndex = 1;

        engine.addEntity(entityBuilder.getEntity());

        return entityBuilder.getEntity();
    }

    private Meter createMeterWidgetFor(Meter.MeterConfig meterConfig) {
        return new MeterImpl(skin, 0.2f, 1, meterConfig);
    }


    private void createIncrementButton(Meter meter, boolean isDisabled) {
        entityCreator.setSummonerType(SummonerType.Button);
        entityCreator.getCreationSettings(ButtonTemplateComponent.class).activateEvent = isDisabled? () -> {} : () -> incrementValue(meter);
        entityCreator.createEntityOn(pos.x, pos.y + 0.75f,"up");
    }

    private void incrementValue(Meter meter) {
        meter.addValue(5);
    }

    private void createDecrementButton(Meter meter, boolean isDisabled) {
        entityCreator.setSummonerType(SummonerType.Button);
        entityCreator.getCreationSettings(ButtonTemplateComponent.class).activateEvent = isDisabled? () -> {} : () -> decrementValue(meter);
        entityCreator.createEntityOn(pos.x, pos.y - 0.75f,"down");
    }

    private void decrementValue(Meter meter) {
        meter.addValue(-5);
    }

    @Override
    public SummonerType getType() {
        return SummonerType.BattlefieldSetting;
    }
}
