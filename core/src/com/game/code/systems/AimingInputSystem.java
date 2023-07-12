package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.code.components.AimsComponent;
import com.game.code.components.CanonComponent;
import com.game.code.components.DeadComponent;
import com.game.code.components.PlayerComponent;

public class AimingInputSystem extends IteratingSystem {

    private final Viewport viewport;
    protected final Vector2 aimingPoint = new Vector2();

    public AimingInputSystem(Viewport viewport) {
        super(Family.all(PlayerComponent.class).one(CanonComponent.class)
                .exclude(DeadComponent.class).get(), 10);

        this.viewport = viewport;
    }

    @Override
    public void update(float deltaTime) {
        processInput();

        super.update(deltaTime);
    }

    private void processInput() {
        aimingPoint.set(Gdx.input.getX(), Gdx.input.getY());

        viewport.unproject(aimingPoint);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AimsComponent aimsC = getEngine().createComponent(AimsComponent.class);
        aimsC.target.set(aimingPoint);

        entity.add(aimsC);
    }
}
