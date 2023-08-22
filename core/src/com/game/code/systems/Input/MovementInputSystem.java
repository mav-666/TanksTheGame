package com.game.code.systems.Input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.game.code.components.*;
import com.game.code.components.MovesComponent;

public class MovementInputSystem extends IteratingSystem {

    private int turningDirection = 0;
    private int movementDirection = 0;

    public MovementInputSystem() {
        super(Family.all(PlayerComponent.class, MobilityComponent.class).exclude(DeadComponent.class).get(), 10);
    }

    @Override
    public void update(float deltaTime) {
        processInput();

        if(turningDirection != 0 || movementDirection != 0) {
            super.update(deltaTime);
        }
    }

    private void processInput() {
        turningDirection = 0;
        movementDirection = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            turningDirection -= 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            turningDirection += 1;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            movementDirection += 1;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            movementDirection -= 1;
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovesComponent moves = getEngine().createComponent(MovesComponent.class);

        if(movementDirection != 0)
            moves.turingDirection = this.turningDirection;
        moves.movementDirection = this.movementDirection;

        entity.add(moves);
    }
}
