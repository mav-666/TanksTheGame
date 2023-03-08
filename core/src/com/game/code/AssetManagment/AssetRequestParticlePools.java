package com.game.code.AssetManagment;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;

import java.util.HashMap;

public interface AssetRequestParticlePools extends AssetRequest {

    void passParticleAssets(AssetRequestProcessor assets, HashMap<ParticleEffect, ParticleEffectPool> particlePools);

    default ParticleEffectPool getParticlePool(HashMap<ParticleEffect, ParticleEffectPool> particlePools, ParticleEffect particleEffect) {
        if(!particlePools.containsKey(particleEffect))
            particlePools.put(particleEffect, new ParticleEffectPool(particleEffect, 0, 100));

        return particlePools.get(particleEffect);
    }
}
