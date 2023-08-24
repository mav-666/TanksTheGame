package com.game.code.systems.Actions;

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

    private final Vector2 linearVelocity = new Vector2();

    public MovementSystem() {
        super(Family.all(MovesComponent.class, MobilityComponent.class, BodyComponent.class).get(), 12);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovesComponent moves = Mappers.get(MovesComponent.class, entity);
        MobilityComponent mobility = Mappers.get(MobilityComponent.class, entity);
        Body body  = Mappers.get(BodyComponent.class, entity).body;
        body.setAwake(true);

        if(moves.movementDirection != 0)
            setLinearVelocityFor(body, mobility.movementSpeed, moves.movementDirection, deltaTime);

        if(moves.turingDirection != 0)
            setAngularVelocityFor(body, mobility.agility, moves.turingDirection, deltaTime);

        entity.remove(MovesComponent.class);
    }

    private void setLinearVelocityFor(Body body, float speed, float direction, float deltaTime) {
        float movementSpeed = direction == -1 ? speed * 0.75f : speed;

        linearVelocity.set(0, movementSpeed * direction * deltaTime).rotateRad(body.getAngle());

        body.setLinearVelocity(body.getLinearVelocity().add(linearVelocity));
    }

    private void setAngularVelocityFor(Body body, float agility, float direction, float deltaTime) {
        body.setAngularVelocity(body.getAngularVelocity() + agility * direction * deltaTime);
    }

}
