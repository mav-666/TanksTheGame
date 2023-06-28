package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.game.code.components.DeadComponent;
import com.game.code.components.PlayerComponent;
import com.game.code.components.ProjectileTemplateComponent;
import com.game.code.components.ShootsComponent;

public class ShootingInputSystem extends IteratingSystem {

    public ShootingInputSystem() {
        super(Family.all(PlayerComponent.class, ProjectileTemplateComponent.class)
                .exclude(DeadComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        entity.add(getEngine().createComponent(ShootsComponent.class));
    }
}
