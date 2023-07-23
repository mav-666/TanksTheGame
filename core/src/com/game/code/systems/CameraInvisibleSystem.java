package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.game.code.components.InvisibleComponent;
import com.game.code.components.TextureComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;

public class CameraInvisibleSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();

    private final Camera camera;

    private final Vector3 leftBottomPoint = new Vector3();
    private final Vector3 rightTopPoint = new Vector3();

    public CameraInvisibleSystem(Camera camera) {
        super(Family.all(TransformComponent.class, TextureComponent.class).get());

        this.camera = camera;

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = mappers.get(TransformComponent.class, entity);
        TextureComponent texture = mappers.get(TextureComponent.class, entity);

        float width = texture.width;
        float height = texture.height;

        float posX = transform.position.x - width/2 + texture.offset.x;
        float posY = transform.position.y - height/2 + texture.offset.y;

        leftBottomPoint.set(posX, posY, 0);
        rightTopPoint.set(posX + width - texture.offset.x,
                posY + height - texture.offset.y, 0);

        if(isInvisible())
            entity.add(getEngine().createComponent(InvisibleComponent.class));
        else
            entity.remove(InvisibleComponent.class);
    }

    private boolean isInvisible() {
        Vector3 projectedLeft = camera.project(leftBottomPoint);
        Vector3 projectedRight = camera.project(rightTopPoint);

        return projectedRight.x < -20
            || projectedRight.y < -20
            || projectedLeft.x > Gdx.graphics.getWidth() + 20
            || projectedLeft.y > Gdx.graphics.getHeight() + 20;
    }
}
