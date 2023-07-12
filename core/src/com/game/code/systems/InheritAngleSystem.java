package com.game.code.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.InheritAngleComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;

public class InheritAngleSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();

    public InheritAngleSystem() {
        super(Family.all(TransformComponent.class, InheritAngleComponent.class).get(), 13);


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ComponentMapper<TransformComponent> transformM =  mappers.get(TransformComponent.class);

        Entity original = mappers.get(InheritAngleComponent.class).get(entity).target;

        if(original != null)
            transformM.get(entity).degAngle = transformM.get(original).degAngle;
    }
}
