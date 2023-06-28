package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.DeadComponent;
import com.game.code.components.HealthComponent;
import com.game.code.utils.Mappers;

public class LowHealthDeathSystem extends IteratingSystem {

    private final Mappers mappers;

    public LowHealthDeathSystem() {
        super(Family.all(HealthComponent.class).exclude(DeadComponent.class).get());

        mappers = Mappers.getInstance();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        HealthComponent health = mappers.get(HealthComponent.class).get(entity);

        if(health.health <= 0)
            entity.add(getEngine().createComponent(DeadComponent.class));
    }
}
