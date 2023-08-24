package com.game.code.systems.Attachment;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.game.code.components.CameraFollowedComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.BoundedCamera;
import com.game.code.utils.Mappers;

public class CameraFollowingSystem extends IteratingSystem {

    Camera camera;

    public CameraFollowingSystem(Camera camera) {
        super(Family.all(TransformComponent.class, CameraFollowedComponent.class).get());

        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector2 target = Mappers.get(TransformComponent.class, entity).position;
        lerpToTarget(camera.position, target);
    }

    private void lerpToTarget(Vector3 cameraPos, Vector2 target){
        float t = Math.min(1, target.dst(cameraPos.x, cameraPos.y) * 0.05f);

        cameraPos.add((target.x - cameraPos.x) * t, (target.y - cameraPos.y) * t, 0);
    }
}
