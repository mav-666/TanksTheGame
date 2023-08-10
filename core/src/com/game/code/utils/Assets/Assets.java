package com.game.code.utils.Assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.github.tommyettinger.textra.Font;

public class Assets implements Disposable {

    private static final String TEXTURE_ATLAS_PATH = "TextureAtlas/tankTextures.atlas";

    private final AssetManager assetManager = new AssetManager();
    private final ParticleEffectManager particleManager = new ParticleEffectManager(assetManager);
    private final SkinManager skinManager = new SkinManager(assetManager);

    public void loadSkin() {
        skinManager.loadSkin();
    }

    public void loadParticles() {
        particleManager.loadParticles();
    }

    public void loadTextures() {
        assetManager.load(TEXTURE_ATLAS_PATH, TextureAtlas.class);
    }

    public ParticleEffect getParticle(Particle name) {
        return particleManager.getParticleEffect(name);
    }

    public Skin getSkin() {
        return skinManager.getSkin();
    }

    public TextureAtlas getTextureAtlas() {
        return assetManager.get(TEXTURE_ATLAS_PATH, TextureAtlas.class);
    }

    public Font getFont(String fontName, float size) {
        return skinManager.getFont(fontName, size);
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
