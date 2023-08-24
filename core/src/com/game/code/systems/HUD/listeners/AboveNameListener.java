package com.game.code.systems.HUD.listeners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.components.NameComponent;
import com.game.code.components.TextComponent;
import com.game.code.utils.Mappers;

public class AboveNameListener implements EntityListener {
    public static final Family FAMILY = Family.all(NameComponent.class).get();
    private final ComponentInitializer componentInitializer;
    private final Engine engine;

    public AboveNameListener(Engine engine, ComponentInitializer componentInitializer) {
        this.engine = engine;
        this.componentInitializer = componentInitializer;
    }

    @Override
    public void entityAdded(Entity entity) {
        TextComponent aboveText = createTextComponent();

        aboveText.label.setText(Mappers.get(NameComponent.class, entity).name);

        entity.add(aboveText);
    }

    private TextComponent createTextComponent() {
        TextComponent aboveText = engine.createComponent(TextComponent.class);
        try {
            componentInitializer.initField(aboveText, "label", new String[]{"Pixel", "2"});
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        aboveText.offset.set(0, .6f);

        return aboveText;
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
