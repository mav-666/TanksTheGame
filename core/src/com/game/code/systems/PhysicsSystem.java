package com.game.code.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.game.code.components.BodyComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;

public class PhysicsSystem extends IteratingSystem {

    private static final float TIME_STEP = 1/60f;

    private final Mappers mappers;

    private final World world;
    private float accumulator = 0;

    public PhysicsSystem(World world) {
        super(Family.all(TransformComponent.class, BodyComponent.class).get());

        mappers = Mappers.getInstance();

        this.world = world;
    }

    @Override
    public void update(float deltaTime) {
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while(accumulator >= TIME_STEP) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = mappers.get(TransformComponent.class).get(entity);
        Body body  = mappers.get(BodyComponent.class).get(entity).body;

        transform.position.set(body.getPosition());
        transform.degAngle = (float) Math.toDegrees(body.getAngle());
    }
}
