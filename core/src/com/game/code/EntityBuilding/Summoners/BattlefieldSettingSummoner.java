package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.battlefiled.BattlefieldSettings;
import com.game.code.EntityBuilding.battlefiled.EntityCreator;
import com.game.code.UI.Meter;
import com.game.code.UI.MeterImpl;
import com.game.code.components.*;
import com.github.tommyettinger.textra.TextraLabel;

public class BattlefieldSettingSummoner extends EntityBuilderSummoner {

    private final Skin skin;
    private final EntityCreator entityCreator;
    private final Vector2 pos = new Vector2();

    public BattlefieldSettingSummoner(EntityBuilder entityBuilder, Engine engine, Skin skin, EntityCreator entityCreator) {
        super(entityBuilder, engine);

        this.skin = skin;
        this.entityCreator = entityCreator;
    }

    @Override
    public Entity summonBy(Entity summoner) {
        pos.set(mappers.get(TransformComponent.class, summoner).position);

        String fillingName = mappers.get(SummonsNowComponent.class, summoner).entityName;
        BattlefieldSettings battlefieldSettings = mappers.get(BattlefieldSettingTemplateComponent.class, summoner).battlefieldSettings;

        createFillingSprite(fillingName);

        TextraLabel label = createPercentageLabel();

        Entity meterE = createMeterEntity();

        Meter meter = createMeterWidgetFor(meterE, fillingName, battlefieldSettings);

        ButtonActivation activation = () -> {
            battlefieldSettings.setPercentageOf(fillingName, meter.getValue());
            label.setText("[%50]" +(int)meter.getValue() + "%");
        };

        activation.update();

        createIncrementButton(meter, activation);

        createDecrementButton(meter, activation);

        entityCreator.setSummonerType(SummonerType.BattlefieldSetting);

        return meterE;
    }

    private void createFillingSprite(String fillingName) {
        entityCreator.clearSettings();
        entityCreator.setSummonerType(SummonerType.Sprite);
        entityCreator.createEntityOn(pos.x, pos.y + 1.5f, 1, fillingName);
    }

    private TextraLabel createPercentageLabel() {
        entityBuilder.build("Text");
        TextraLabel label = entityBuilder.getComponent(TextComponent.class).label;
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

    private Meter createMeterWidgetFor(Entity meterE, String fillingName, BattlefieldSettings battlefieldSettings) {
        Meter meter = new MeterImpl(skin, 0.2f, 1, 100);
        meter.setValue(battlefieldSettings.getPercentageOf(fillingName));
        entityBuilder.getComponent(WidgetComponent.class).widget = meter;
        mappers.get(WidgetComponent.class, meterE).widget = meter;

        return meter;
    }

    private void createIncrementButton(Meter meter, ButtonActivation activation) {
        entityCreator.setSummonerType(SummonerType.Button);
        entityCreator.getCreationSettings(ButtonTemplateComponent.class).activateEvent = () -> {incrementFilling(meter); activation.update();};
        entityCreator.createEntityOn(pos.x, pos.y + 0.75f,"up");
    }

    private void incrementFilling(Meter meter) {
        meter.addValue(5);
    }

    private void createDecrementButton(Meter meter, ButtonActivation activation) {
        entityCreator.setSummonerType(SummonerType.Button);
        entityCreator.getCreationSettings(ButtonTemplateComponent.class).activateEvent = () -> {decrementFilling(meter); activation.update();};
        entityCreator.createEntityOn(pos.x, pos.y - 0.75f,"down");
    }

    private void decrementFilling(Meter meter) {
        meter.addValue(-5);
    }

    @Override
    public SummonerType getType() {
        return SummonerType.BattlefieldSetting;
    }

    @FunctionalInterface
    interface ButtonActivation {
        void update();
    }
}
