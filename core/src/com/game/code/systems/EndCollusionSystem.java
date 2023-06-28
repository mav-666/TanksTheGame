package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.EndsCollusionComponent;

public class EndCollusionSystem extends IteratingSystem {

    public EndCollusionSystem() {
        super(Family.all(EndsCollusionComponent.class).get(), 90);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        entity.remove(EndsCollusionComponent.class);
    }
}
