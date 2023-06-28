package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.EntityBuilding.SummoningDirector;
import com.game.code.components.SummonsParticleComponent;

public class ParticleSummoningSystem extends IteratingSystem {

    SummoningDirector summoningDirector;

    public ParticleSummoningSystem(SummoningDirector summoningDirector) {
        super(Family.all(SummonsParticleComponent.class).get(), 91);

        this.summoningDirector = summoningDirector;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        getEngine().addEntity(summoningDirector.summonBy(entity));

        entity.remove(SummonsParticleComponent.class);
    }
}
