package com.game.code.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.ray3k.stripe.FreeTypeSkinLoader;

public class Assets implements Disposable {
    private final static String SKIN_PATH = "Skin/skin.json";
    private final static String SKIN_ATLAS_PATH = "Skin/skin.atlas";

    private static final String PARTICLE_PATH = "ParticleEffect/";
    private final static String PARTICLE_ATLAS_PATH = "ParticleEffect/Particles.atlas";

    private static final String TEXTURE_ATLAS_PATH = "TextureAtlas/tankTextures.atlas";

    private final AssetManager assetManager;
    private final ParticleEffectManager particleManager;

    public Assets() {
        assetManager = new AssetManager();
        assetManager.setLoader(Skin.class, new FreeTypeSkinLoader(assetManager.getFileHandleResolver()));

        particleManager = new ParticleEffectManager();
    }

    public void loadUISkin() {
        SkinLoader.SkinParameter params = new SkinLoader.SkinParameter(SKIN_ATLAS_PATH);
        assetManager.load(SKIN_PATH, Skin.class, params);
        assetManager.finishLoading();

    }

    public void loadParticles() {
        ParticleEffectLoader.ParticleEffectParameter params = new ParticleEffectLoader.ParticleEffectParameter();
        params.atlasFile = PARTICLE_ATLAS_PATH;

        for (Particle particle : Particle.values()) {
            assetManager.load(PARTICLE_PATH + particle.name(), ParticleEffect.class, params);
        }
    }

    public void loadTextures() {
        assetManager.load(TEXTURE_ATLAS_PATH, TextureAtlas.class);
    }

    public ParticleEffect getParticle(Particle name) {
        if(!particleManager.containsParticleEffect(name))
            particleManager.addParticleEffect(name, assetManager.get(PARTICLE_PATH + name.name(), ParticleEffect.class));
        return particleManager.getParticleEffect(name);
    }

    public Skin getSkin() {
        return assetManager.get(SKIN_PATH, Skin.class);
    }

    public TextureAtlas getTextureAtlas() {
        return assetManager.get(TEXTURE_ATLAS_PATH, TextureAtlas.class);
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
