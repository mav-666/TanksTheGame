package com.game.code.systems.Attachment;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.game.code.components.FollowsCameraComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;

public class FollowCameraSystem extends IteratingSystem {
    private final Camera camera;

    public FollowCameraSystem(Camera camera) {
        super(Family.all(FollowsCameraComponent.class, TransformComponent.class).get());
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Mappers.get(TransformComponent.class, entity).position.set(camera.position.x, camera.position.y);
    }
}
