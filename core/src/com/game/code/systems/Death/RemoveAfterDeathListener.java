package com.game.code.systems.Death;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.game.code.components.DeadComponent;
import com.game.code.components.DestroyedComponent;
import com.game.code.components.RemoveAfterDeathComponent;

public class RemoveAfterDeathListener implements EntityListener {
    public static final Family FAMILY = Family.all(DeadComponent.class, RemoveAfterDeathComponent.class).get();

    private final Engine engine;

    public RemoveAfterDeathListener(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void entityAdded(Entity entity) {
        entity.add(engine.createComponent(DestroyedComponent.class));
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
