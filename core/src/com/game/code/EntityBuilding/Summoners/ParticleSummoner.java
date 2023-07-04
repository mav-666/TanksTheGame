package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.CreatableEntity;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.components.ParticleComponent;
import com.game.code.components.SummonsParticleComponent;
import com.game.code.components.TransformComponent;

import java.util.Optional;

public class ParticleSummoner extends EntityBuilderSummoner {

    private final ComponentInitializer componentInitializer;

    private Entity summoner;

    public ParticleSummoner(EntityBuilder entityBuilder, Engine engine, ComponentInitializer componentInitializer) {
        super(entityBuilder, engine);
        this.componentInitializer = componentInitializer;
    }

    @Override
    public Entity summonBy(Entity summoner) {
        this.summoner = summoner;

        entityBuilder.build(CreatableEntity.Particle.name());

        initPosition();

        initParticleEffect();

        engine.addEntity(entityBuilder.getEntity());

        return entityBuilder.getEntity();
    }

    private void initParticleEffect() {
        var particleName  = getParticleName();

        if(particleName.isEmpty())
            return;

        ParticleComponent particle = entityBuilder.getComponent(ParticleComponent.class);

        try {
            componentInitializer.initField(particle, ParticleComponent.class.getFields()[0].getName(), particleName.get());
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
        particleTransform.zIndex = summonerTransform.zIndex - 1;
    }
}
