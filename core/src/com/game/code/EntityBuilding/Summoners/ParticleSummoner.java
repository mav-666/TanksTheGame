package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.SummonerType;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.components.ParticleComponent;
import com.game.code.components.SummonsNowComponent;
import com.game.code.components.TransformComponent;

import java.util.Optional;

public class ParticleSummoner extends EntitySummoner {

    protected final ComponentInitializer componentInitializer;

    private Entity summoner;

    ParticleSummoner(EntityBuilder entityBuilder, Engine engine, ComponentInitializer componentInitializer) {
        super(entityBuilder, engine);

        this.componentInitializer = componentInitializer;
    }

    @Override
    public Entity summonBy(Entity summoner) {
        this.summoner = summoner;

        entityBuilder.build(SummonerType.Particle.name());

        initTransformBy(summoner);

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
        ComponentMapper<SummonsNowComponent> summonM = mappers.get(SummonsNowComponent.class);
        if(summonM.has(summoner))
            return Optional.of(summonM.get(summoner).entityName);
        return Optional.empty();
    }

    protected void initTransformBy(Entity summoner) {
        super.initTransformBy(summoner);

        entityBuilder.getComponent(TransformComponent.class).zIndex--;
    }
}
