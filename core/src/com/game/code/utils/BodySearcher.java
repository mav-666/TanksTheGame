package com.game.code.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.components.BodyComponent;
import com.game.code.components.ConnectedComponent;

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
        ComponentMapper<BodyComponent> bodyM = mappers.get(BodyComponent.class);

        if(bodyM.has(entity))
            return bodyM.get(entity).body;

        return null;
    }

    private Body searchForConnectionComponent(Entity entity) {
        ComponentMapper<ConnectedComponent> connectedM = mappers.get(ConnectedComponent.class);

        if(connectedM.has(entity)) {
            return connectedM.get(entity).target;
        }
        return null;
    }
}
