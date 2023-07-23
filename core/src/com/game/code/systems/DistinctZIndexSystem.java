package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;

public class DistinctZIndexSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();

    public DistinctZIndexSystem() {
        super(Family.all(TransformComponent.class).get(), 60);


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = mappers.get(TransformComponent.class, entity);

        if(transform.zIndex == (int) transform.zIndex)
            transform.zIndex -= (Math.random() * (0.1 - 0.01)) + 0.01;
    }


}
