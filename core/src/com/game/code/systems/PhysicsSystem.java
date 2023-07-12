package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.game.code.components.BodyComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;

public class PhysicsSystem extends IntervalIteratingSystem {

    private static final float TIME_STEP = 1/60f;

    private final Mappers mappers = Mappers.getInstance();

    private final World world;

    public PhysicsSystem(World world) {
        super(Family.all(TransformComponent.class, BodyComponent.class).get(), TIME_STEP);

        this.world = world;
    }

    @Override
    public void updateInterval() {
            world.step(TIME_STEP, 6, 2);
            super.updateInterval();
    }

    @Override
    protected void processEntity(Entity entity) {
        TransformComponent transform = mappers.get(TransformComponent.class).get(entity);
        Body body  = mappers.get(BodyComponent.class).get(entity).body;

        transform.position.set(body.getPosition());
        transform.degAngle = (float) Math.toDegrees(body.getAngle());
    }
}
