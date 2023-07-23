package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.components.BodyComponent;
import com.game.code.components.ConnectedComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;

public class ConnectionSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();

    public ConnectionSystem() {
        super(Family.all(TransformComponent.class, ConnectedComponent.class).get(), 1);


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = mappers.get(TransformComponent.class, entity);
        ConnectedComponent connected = mappers.get(ConnectedComponent.class, entity);

        transform.position.set(connected.target.getPosition()).sub(connected.connectionPoint);

        if(mappers.has(BodyComponent.class, entity)) {
            Body body = mappers.get(BodyComponent.class, entity).body;
            body.setTransform(connected.target.getPosition(), body.getAngle());
        }
    }
}
