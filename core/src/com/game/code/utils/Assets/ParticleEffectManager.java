package com.game.code.utils.Assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.ObjectMap;

public class ParticleEffectManager {

    private static final String PARTICLE_PATH = "ParticleEffect/";
    private final static String PARTICLE_ATLAS_PATH = "ParticleEffect/Particles.atlas";

    private final ObjectMap<Particle, ParticleEffectPool> pools = new ObjectMap<>();
    private final AssetManager assetManager;

    ParticleEffectManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void loadParticles() {
        ParticleEffectLoader.ParticleEffectParameter params = new ParticleEffectLoader.ParticleEffectParameter();
        params.atlasFile = PARTICLE_ATLAS_PATH;

        for (Particle particle : Particle.values()) {
            assetManager.load(PARTICLE_PATH + particle.name(), ParticleEffect.class, params);
        }
    }

    public ParticleEffect getParticleEffect(Particle name) {
        if(!containsParticleEffect(name))
            addParticleEffect(name, assetManager.get(PARTICLE_PATH + name.name(), ParticleEffect.class));
        return pools.get(name).obtain();
    }

    public boolean containsParticleEffect(Particle name) {
        return pools.containsKey(name);
    }

    public void addParticleEffect(Particle name, ParticleEffect particleEffect) {
        pools.put(name, new ParticleEffectPool(particleEffect, 5, 20));
    }
}

