package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.game.code.components.CameraFollowedComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.BoundedCamera;
import com.game.code.utils.Mappers;

public class CameraFollowingSystem extends IteratingSystem {

    private final Mappers mappers;

    BoundedCamera boundedCamera;

    public CameraFollowingSystem(BoundedCamera boundedCamera) {
        super(Family.all(TransformComponent.class, CameraFollowedComponent.class).get());

        this.boundedCamera = boundedCamera;

        mappers = Mappers.getInstance();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector2 pos = mappers.get(TransformComponent.class).get(entity).position;
        boundedCamera.follow(pos);
    }
}
