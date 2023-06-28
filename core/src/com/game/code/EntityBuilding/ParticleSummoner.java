package com.game.code.EntityBuilding;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.game.code.components.ParticleComponent;
import com.game.code.components.SummonsParticleComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;

import java.util.Optional;

public class ParticleSummoner implements SummoningDirector {

    private final Mappers mappers;
    private final EntityBuilder entityBuilder;

    private Entity summoner;

    public ParticleSummoner(EntityBuilder entityBuilder) {
        this.entityBuilder = entityBuilder;

        mappers = Mappers.getInstance();
    }

    @Override
    public Entity summonBy(Entity summoner) {
        this.summoner = summoner;

        entityBuilder.build(Entities.Particle.name());

        initPosition();

        initParticleEffect();

        return entityBuilder.getEntity();
    }

    private void initParticleEffect() {
        var particleName  = getParticleName();

        if(particleName.isEmpty())
            return;

        ParticleComponent particle = entityBuilder.getComponent(ParticleComponent.class);

        try {
            entityBuilder.initField(particle, ParticleComponent.class.getFields()[0].getName(), particleName.get());
        } catch (NoSuchFieldException e) {
            Gdx.app.log("Error", "huh?");
            e.printStackTrace();
        }

        particle.particleEffect.start();
    }

    private Optional<String> getParticleName() {
        ComponentMapper<SummonsParticleComponent> summonM = mappers.get(SummonsParticleComponent.class);
        if(summonM.has(summoner))
            return Optional.of(summonM.get(summoner).particleName);
        return Optional.empty();
    }

    private void initPosition() {
        TransformComponent particleTransform = entityBuilder.getComponent(TransformComponent.class);
        TransformComponent summonerTransform = mappers.get(TransformComponent.class).get(summoner);

        particleTransform.position.set(summonerTransform.position);
        particleTransform.zIndex = summonerTransform.zIndex;
    }
}
