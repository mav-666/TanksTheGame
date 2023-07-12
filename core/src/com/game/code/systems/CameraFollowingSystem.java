package com.game.code.systems;

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

    private final Mappers mappers = Mappers.getInstance();

    Camera camera;

    public CameraFollowingSystem(Camera camera) {
        super(Family.all(TransformComponent.class, CameraFollowedComponent.class).get());

        this.camera = camera;


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector2 target = mappers.get(TransformComponent.class).get(entity).position;
        lerpToTarget(camera.position, target);
    }

    private void lerpToTarget(Vector3 cameraPos, Vector2 target){
        cameraPos.add((target.x - cameraPos.x) * .05f, (target.y - cameraPos.y) * .05f, 0);
    }
}
