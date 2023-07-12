package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.game.code.components.DeadComponent;
import com.game.code.components.PlayerComponent;
import com.game.code.components.ProjectileTemplateComponent;
import com.game.code.components.ShootsComponent;


public class ShootingInputSystem extends IntervalIteratingSystem {
    private static final float TIME_STEP = 1/60f;

    public ShootingInputSystem() {
        super(Family.all(PlayerComponent.class, ProjectileTemplateComponent.class)
                .exclude(DeadComponent.class).get(), TIME_STEP, 10);
    }

    @Override
    public void updateInterval() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            updateAfterCheck();
        }
    }

    protected void updateAfterCheck() {
        super.updateInterval();
    }

    @Override
    protected void processEntity(Entity entity) {
        entity.add(getEngine().createComponent(ShootsComponent.class));
    }
}
