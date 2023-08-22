package com.game.code.systems.Attachment;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.code.components.FollowCursorComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;

public class FollowCursorSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();
    private final Viewport viewport;

    private Vector2 cursorPos = new Vector2();

    public FollowCursorSystem(Viewport viewport) {
        super(Family.all(FollowCursorComponent.class, TransformComponent.class).get());

        this.viewport = viewport;
    }

    @Override
    public void update(float deltaTime) {
        calcCursorPosition();
        super.update(deltaTime);
    }

    private void calcCursorPosition() {
        cursorPos.set(Gdx.input.getX(), Gdx.input.getY());

        viewport.unproject(cursorPos);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        mappers.get(TransformComponent.class, entity).position.set(cursorPos);
    }
}
