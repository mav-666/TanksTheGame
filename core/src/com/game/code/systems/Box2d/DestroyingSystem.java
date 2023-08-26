package com.game.code.systems.Box2d;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import com.game.code.components.BodyComponent;
import com.game.code.components.DestroyedComponent;
import com.game.code.components.ParticleComponent;
import com.game.code.utils.Mappers;

public class DestroyingSystem extends IteratingSystem {
    private final World world;

    public DestroyingSystem(World world) {
        super(Family.all(DestroyedComponent.class).get(), 1);

        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        destroyBody(entity);
        destroyParticle(entity);

        getEngine().removeEntity(entity);

    }

    private void destroyBody(Entity entity) {
        ComponentMapper<BodyComponent> bodyM = Mappers.getMapper(BodyComponent.class);

        if(bodyM.has(entity)) {
            world.destroyBody(bodyM.get(entity).body);
            bodyM.get(entity).body = null;
        }
    }

    private void destroyParticle(Entity entity) {
        ComponentMapper<ParticleComponent> particleM = Mappers.getMapper(ParticleComponent.class);

        if(particleM.has(entity))
            particleM.get(entity).particleEffect.dispose();
    }
}
