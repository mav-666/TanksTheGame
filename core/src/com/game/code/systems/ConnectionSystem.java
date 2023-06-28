package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.ConnectedComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;

public class ConnectionSystem extends IteratingSystem {

    private final Mappers mappers;

    public ConnectionSystem() {
        super(Family.all(TransformComponent.class, ConnectedComponent.class).get());

        mappers = Mappers.getInstance();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = mappers.get(TransformComponent.class).get(entity);
        ConnectedComponent connected = mappers.get(ConnectedComponent.class).get(entity);

        transform.position.set(connected.target.getPosition()).sub(connected.connectionPoint);

    }
}
