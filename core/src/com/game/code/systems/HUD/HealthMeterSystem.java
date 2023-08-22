package com.game.code.systems.HUD;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.UI.HealthMeter;
import com.game.code.UI.Meter;
import com.game.code.components.*;
import com.game.code.utils.Mappers;

public class HealthMeterSystem extends EntitySystem implements EntityListener {
    public static final Family FAMILY = Family.all(PlayerComponent.class, HealthComponent.class).get();

    private final Mappers mappers = Mappers.getInstance();

    private final Viewport viewport;
    private final Skin skin;
    private final Entity meter;

    private HealthComponent healthC;

    private HealthMeter healthMeter;
    private Vector2 offset;

    public HealthMeterSystem(Viewport viewport, EntityBuilder entityBuilder, Skin skin) {
        this.viewport = viewport;
        this.skin = skin;

        entityBuilder.build("GUIElement");
        meter = entityBuilder.getEntity();

        this.setProcessing(false);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        getEngine().addEntityListener(FAMILY, this);
    }

    @Override
    public void entityAdded(Entity entity) {
        healthC = mappers.get(HealthComponent.class, entity);
        createWidget(skin);
        createHealthMeter(healthMeter);

        this.setProcessing(true);
    }

    @Override
    public void entityRemoved(Entity entity) {

    }

    private void createWidget(Skin skin) {
        healthMeter = new HealthMeter(skin, new Meter.MeterConfig(healthC.currentHP));
    }

    private void createHealthMeter(HealthMeter healthMeter) {
        WidgetComponent widgetC = mappers.get(WidgetComponent.class, meter);

        widgetC.widget = healthMeter;
        offset = widgetC.offset;

        getEngine().addEntity(meter);
    }

    @Override
    public void update(float deltaTime) {
        offset.set(viewport.getWorldWidth()/2f - 0.5f, -viewport.getWorldHeight()/2f + 0.25f);
        healthMeter.setValue(healthC.currentHP);
    }
}
