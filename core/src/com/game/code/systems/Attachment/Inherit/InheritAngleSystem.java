package com.game.code.systems.Attachment.Inherit;

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

    public InheritAngleSystem() {
        super(Family.all(TransformComponent.class, InheritAngleComponent.class).get(), 13);


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ComponentMapper<TransformComponent> transformM = Mappers.getMapper(TransformComponent.class);

        Entity original = Mappers.get(InheritAngleComponent.class, entity).target;
        float degAngle = transformM.get(original).degAngle;

        transformM.get(entity).degAngle = degAngle;

        if(Mappers.has(BodyComponent.class, entity)) {
            Body body = Mappers.get(BodyComponent.class, entity).body;

            body.setTransform(body.getPosition(), (float) Math.toRadians(degAngle));
        }
    }
}
