package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.DeadComponent;
import com.game.code.components.DestroyedComponent;
import com.game.code.components.RemoveAfterDeathComponent;

public class RemoveAfterDeathSystem extends IteratingSystem {

    public RemoveAfterDeathSystem() {
        super(Family.all(DeadComponent.class, RemoveAfterDeathComponent.class).get(), 98);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        entity.add(getEngine().createComponent(DestroyedComponent.class));

    }
}
