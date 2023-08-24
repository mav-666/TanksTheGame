package com.game.code.systems.Death.listeners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Color;
import com.game.code.components.ColorComponent;
import com.game.code.components.DeadComponent;
import com.game.code.utils.Mappers;



public class FadeAfterDeathListener implements EntityListener {
    public static final Family FAMILY = Family.all(DeadComponent.class).get();
    private final Engine engine;

    private final Color fadeColor = Color.valueOf("646464");

    public FadeAfterDeathListener(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void entityAdded(Entity entity) {
        Mappers.getOrCreate(ColorComponent.class, entity).color.set(fadeColor);
    }

    @Override
    public void entityRemoved(Entity entity) {
        Mappers.get(ColorComponent.class, entity).color.set(Color.WHITE);
    }
}
