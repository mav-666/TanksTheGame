package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.DestroyedComponent;
import com.game.code.components.FragileParticleComponent;
import com.game.code.components.SummonsParticleComponent;
import com.game.code.utils.Mappers;

public class FragileParticleSystem extends IteratingSystem {

    private final Mappers mappers;

    public FragileParticleSystem() {
        super(Family.all(FragileParticleComponent.class, DestroyedComponent.class).get());

        mappers = Mappers.getInstance();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SummonsParticleComponent summonsParticle = getEngine().createComponent(SummonsParticleComponent.class);

        summonsParticle.particleName = mappers.get(FragileParticleComponent.class).get(entity).particleName;

        entity.add(summonsParticle);
    }
}
