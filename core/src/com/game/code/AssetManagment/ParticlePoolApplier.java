package com.game.code.AssetManagment;

import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;

public class ParticlePoolApplier extends AssetProcessorDecorator implements Disposable {

    private final ParticleEffectLoader.ParticleEffectParameter parameter;

    private final HashMap<ParticleEffect, ParticleEffectActorPool> particlePools;

    public ParticlePoolApplier(AssetProcessor assetProcessor) {
        super(assetProcessor);

        this.particlePools = new HashMap<>();

        parameter = new ParticleEffectLoader.ParticleEffectParameter();
        parameter.atlasFile = "ParticleEffect/Particles.atlas";
    }

    @Override
    public <T> void loadAsset(String fileName, Class<T> clazz) {
        if(clazz == ParticleEffect.class) {
            loadAsset(fileName, ParticleEffect.class, parameter);
            return;
        }

        super.loadAsset(fileName, clazz);
    }

    @Override
    public void sendAsset(AssetRequest assetRequest) {
        if(assetRequest instanceof AssetRequestParticlePools)
            ((AssetRequestParticlePools) assetRequest).passParticleAssets(this, particlePools);
        super.sendAsset(assetRequest);
    }

    @Override
    public void dispose() {
        particlePools.values().forEach(Pool::clear);
    }
}
