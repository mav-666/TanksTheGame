package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.components.BodyComponent;
import com.game.code.components.MobilityComponent;
import com.game.code.components.MovesComponent;
import com.game.code.utils.Mappers;


public class MovementSystem extends IteratingSystem {

    private final Mappers mappers;

    public MovementSystem() {
        super(Family.all(MovesComponent.class, MobilityComponent.class, BodyComponent.class).get(), 10);

        mappers = Mappers.getInstance();
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovesComponent moves = mappers.get(MovesComponent.class).get(entity);
        MobilityComponent mobility = mappers.get(MobilityComponent.class).get(entity);
        Body body  = mappers.get(BodyComponent.class).get(entity).body;

        float movementSpeed = moves.movementDirection == -1 ? mobility.movementSpeed * 0.75f : mobility.movementSpeed;

        body.setLinearVelocity(body.getLinearVelocity().add(
                new Vector2(0, movementSpeed * deltaTime * moves.movementDirection).rotateRad(body.getAngle())));

        body.setAngularVelocity(body.getAngularVelocity() + mobility.agility * deltaTime * moves.turingDirection);

        entity.remove(MovesComponent.class);
    }
}
