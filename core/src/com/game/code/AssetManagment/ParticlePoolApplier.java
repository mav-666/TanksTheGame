package com.game.code.AssetManagment;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;

public class ParticlePoolApplier extends AssetRequestProcessor implements AssetStrategy<ParticleEffect> {
    private final ParticleEffectLoader.ParticleEffectParameter parameter;

    private final HashMap<ParticleEffect, ParticleEffectPool> particlePools;

    public ParticlePoolApplier(AssetManager assetManager, HashMap<ParticleEffect, ParticleEffectPool> particlePools) {
        super(assetManager);
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
    public void respondOnRequest(AssetRequest client) {
        if(client instanceof AssetRequestParticlePools)
            ((AssetRequestParticlePools) client).passParticleAssets(this, particlePools);
    }

    @Override
    public void dispose() {
        super.dispose();
        particlePools.values().forEach(Pool::clear);
    }
}
