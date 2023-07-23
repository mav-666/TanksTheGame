package com.game.code.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.components.BodyComponent;
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
        ComponentMapper<TransformComponent> transformM = mappers.getMapper(TransformComponent.class);

        Entity original = mappers.get(InheritAngleComponent.class, entity).target;
        float degAngle = transformM.get(original).degAngle;

        transformM.get(entity).degAngle = degAngle;

        if(mappers.has(BodyComponent.class, entity)) {
            Body body = mappers.get(BodyComponent.class, entity).body;

            body.setTransform(body.getPosition(), (float) Math.toRadians(degAngle));
        }
    }
}
