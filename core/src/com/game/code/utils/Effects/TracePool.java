package com.game.code.utils.Effects;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.Pool;
import com.game.code.AssetManagment.AssetProcessor;
import com.game.code.AssetManagment.AssetRequestParticlePools;
import com.game.code.AssetManagment.ParticleEffectActorPool;

import java.util.HashMap;

public class TracePool extends Pool<Trace> implements AssetRequestParticlePools {
    ParticleEffectActorPool effectActorPool;

    @Override
    protected Trace newObject() {
        return new Trace(effectActorPool.obtain());
    }

    @Override
    public void request(AssetProcessor assetRequestProcessor) {
        assetRequestProcessor.receiveRequest("trace", ParticleEffect.class, this);
    }

    @Override
    public void passAssets(AssetProcessor assets) {
    }

    @Override
    public void passParticleAssets(AssetProcessor assets, HashMap<ParticleEffect, ParticleEffectActorPool> particlePools) {
        effectActorPool = getParticlePool(particlePools, assets.get("trace", ParticleEffect.class));
    }
}
