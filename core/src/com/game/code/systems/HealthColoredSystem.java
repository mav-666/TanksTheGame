package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.HealthComponent;
import com.game.code.components.TextureComponent;
import com.game.code.utils.Mappers;

public class HealthColoredSystem extends IteratingSystem {

    private final Mappers mappers;

    public HealthColoredSystem() {
        super(Family.all(HealthComponent.class, TextureComponent.class).get());

        mappers = Mappers.getInstance();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        HealthComponent health = mappers.get(HealthComponent.class).get(entity);
        TextureComponent texture = mappers.get(TextureComponent.class).get(entity);

        texture.color.set(1, health.health/100, health.health/100,1);
    }
}
