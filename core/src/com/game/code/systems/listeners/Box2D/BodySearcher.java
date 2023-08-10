package com.game.code.systems.listeners.Box2D;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.components.BodyComponent;
import com.game.code.components.ConnectedComponent;
import com.game.code.utils.Mappers;

import java.util.Optional;

public class BodySearcher {

    private final Mappers mappers = Mappers.getInstance();

    public BodySearcher() {

    }

    public Optional<Body> searchForBodyIn(Entity entity) {
        Optional<Body> body;

        body = Optional.ofNullable(searchForBodyComponent(entity));

        if(body.isEmpty())
            body = Optional.ofNullable(searchForConnectionComponent(entity));

        return body;
    }

    private Body searchForBodyComponent(Entity entity) {
        ComponentMapper<BodyComponent> bodyM = mappers.getMapper(BodyComponent.class);

        if(bodyM.has(entity))
            return bodyM.get(entity).body;

        return null;
    }

    private Body searchForConnectionComponent(Entity entity) {
        ComponentMapper<ConnectedComponent> connectedM = mappers.getMapper(ConnectedComponent.class);

        if(connectedM.has(entity)) {
            return connectedM.get(entity).target;
        }
        return null;
    }
}
