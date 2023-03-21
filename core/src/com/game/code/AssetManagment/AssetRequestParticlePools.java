package com.game.code.AssetManagment;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;

import java.util.HashMap;

public interface AssetRequestParticlePools extends AssetRequest {

    void passParticleAssets(AssetRequestProcessor assets, HashMap<ParticleEffect, ParticleEffectActorPool> particlePools);

    default ParticleEffectActorPool getParticlePool(HashMap<ParticleEffect, ParticleEffectActorPool> particlePools,
                                                    ParticleEffect particleEffect) {
        if(!particlePools.containsKey(particleEffect))
            particlePools.put(particleEffect, new ParticleEffectActorPool(particleEffect));

        return particlePools.get(particleEffect);
    }
}
