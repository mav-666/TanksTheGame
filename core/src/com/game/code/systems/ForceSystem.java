package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.components.BodyComponent;
import com.game.code.components.ForcedComponent;
import com.game.code.utils.Mappers;

public class ForceSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();

    public ForceSystem() {
        super(Family.all(BodyComponent.class, ForcedComponent.class).get());


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Body body = mappers.get(BodyComponent.class, entity).body;
        Vector2 force = mappers.get(ForcedComponent.class, entity).force;

        body.applyForceToCenter(force, true);
    }
}