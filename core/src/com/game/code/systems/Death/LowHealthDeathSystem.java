package com.game.code.systems.Death;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.DeadComponent;
import com.game.code.components.HealthComponent;
import com.game.code.components.ImmortalComponent;
import com.game.code.utils.Mappers;

public class LowHealthDeathSystem extends IteratingSystem {

    public LowHealthDeathSystem() {
        super(Family.all(HealthComponent.class).exclude(ImmortalComponent.class, DeadComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        HealthComponent health = Mappers.get(HealthComponent.class, entity);

        if(health.currentHP <= 0)
            entity.add(getEngine().createComponent(DeadComponent.class));
    }
}
