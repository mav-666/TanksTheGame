package com.game.code.utils;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.ObjectMap;

public class ParticleEffectManager {

    private final ObjectMap<Particle, ParticleEffectPool> pools;

    ParticleEffectManager() {
        pools = new ObjectMap<>();
    }

    public void addParticleEffect(Particle name, ParticleEffect particleEffect) {
        pools.put(name, new ParticleEffectPool(particleEffect, 5, 20));
    }
    
    public ParticleEffect getParticleEffect(Particle name) {

        return pools.get(name).obtain();
    }

    public boolean containsParticleEffect(Particle name) {
        return pools.containsKey(name);
    }
}

