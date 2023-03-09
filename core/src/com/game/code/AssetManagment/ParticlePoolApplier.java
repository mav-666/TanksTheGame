package com.game.code.AssetManagment;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;

public class ParticlePoolApplier implements AssetStrategy<ParticleEffect> {
    private final ParticleEffectLoader.ParticleEffectParameter parameter;

    private final HashMap<ParticleEffect, ParticleEffectActorPool> particlePools;

    public ParticlePoolApplier(HashMap<ParticleEffect, ParticleEffectActorPool> particlePools) {
        this.particlePools = particlePools;

        this.parameter = new ParticleEffectLoader.ParticleEffectParameter();
        parameter.atlasFile = "ParticleEffect/Particles.atlas";
    }

    @Override
    public Class<ParticleEffect> getType() {
        return ParticleEffect.class;
    }

    @Override
    public ParticleEffectLoader.ParticleEffectParameter getParameter() {
        return parameter;
    }

    @Override
    public void respondOnRequest(AssetRequestProcessor assetRequestProcessor, AssetRequest client) {
        if(client instanceof AssetRequestParticlePools)
            ((AssetRequestParticlePools) client).passParticleAssets(assetRequestProcessor, particlePools);
    }

    @Override
    public void dispose() {
        particlePools.values().forEach(Pool::clear);
    }
}
