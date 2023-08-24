package com.game.code.systems.Box2d;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.components.BodyComponent;
import com.game.code.components.ConnectedComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;

public class ConnectionSystem extends IteratingSystem {

    public ConnectionSystem() {
        super(Family.all(TransformComponent.class, ConnectedComponent.class).get(), 1);


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = Mappers.get(TransformComponent.class, entity);
        ConnectedComponent connected = Mappers.get(ConnectedComponent.class, entity);

        transform.position.set(connected.target.getPosition()).sub(connected.connectionPoint);

        if(Mappers.has(BodyComponent.class, entity)) {
            Body body = Mappers.get(BodyComponent.class, entity).body;
            body.setTransform(connected.target.getPosition(), body.getAngle());
        }
    }
}
