package com.game.code.utils.Assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.ObjectMap;

public class ParticleEffectAssetLoader {

    private static final String PARTICLE_PATH = "ParticleEffect/";
    private final static String PARTICLE_ATLAS_PATH = "ParticleEffect/Particles.atlas";

    private final ObjectMap<ParticleType, ParticleEffectPool> loadedParticles = new ObjectMap<>();
    private final AssetManager assetManager;

    ParticleEffectAssetLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void loadParticles() {
        ParticleEffectLoader.ParticleEffectParameter params = new ParticleEffectLoader.ParticleEffectParameter();
        params.atlasFile = PARTICLE_ATLAS_PATH;

        for (ParticleType particleType : ParticleType.values()) {
            assetManager.load(PARTICLE_PATH + particleType.name(), ParticleEffect.class, params);
        }
    }

    public ParticleEffect getParticleEffect(ParticleType name) {
        if(!loadedParticles.containsKey(name))
            addParticleEffect(name, assetManager.get(PARTICLE_PATH + name.name(), ParticleEffect.class));
        return loadedParticles.get(name).obtain();
    }

    public void addParticleEffect(ParticleType name, ParticleEffect particleEffect) {
        loadedParticles.put(name, new ParticleEffectPool(particleEffect, 5, 20));
    }
}

