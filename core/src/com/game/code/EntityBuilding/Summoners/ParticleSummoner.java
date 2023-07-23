package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.components.ParticleComponent;
import com.game.code.components.SummonsComponent;
import com.game.code.components.SummonsNowComponent;
import com.game.code.components.TransformComponent;

public class ParticleSummoner extends EntitySummoner {

    protected final ComponentInitializer componentInitializer;

    private ParticleComponent particle;

    ParticleSummoner(EntityBuilder entityBuilder, Engine engine, ComponentInitializer componentInitializer) {
        super(entityBuilder, engine);

        this.componentInitializer = componentInitializer;
    }

    @Override
    protected void build() {
        entityBuilder.build(SummonerType.Particle.name());
    }

    @Override
    protected void init(Entity summoner) {
        initParticleEffect(mappers.get(SummonsNowComponent.class, summoner).entityName);
        super.init(summoner);
    }

    private void initParticleEffect(String particleName) {
        if(particleName.isEmpty())
            return;

        particle = entityBuilder.getComponent(ParticleComponent.class);

        try {
            componentInitializer.initField(particle, ParticleComponent.class.getFields()[0].getName(), particleName);
        } catch (NoSuchFieldException e) {
            Gdx.app.log("Error", "huh?");
            e.printStackTrace();
        }

        particle.particleEffect.start();
    }

    protected void initTransformBy(Entity summoner) {
        super.initTransformBy(summoner);

        entityBuilder.getComponent(TransformComponent.class).zIndex--;
    }

    @Override
    protected void initScale(SummonsComponent summonsComponent) {
        particle.particleEffect.scaleEffect(summonsComponent.scaleX, summonsComponent.scaleY);
    }
}
