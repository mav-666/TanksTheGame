package com.game.code.utils.Assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.github.tommyettinger.textra.Font;

public class Assets implements Disposable {

    private static final String TEXTURE_ATLAS_PATH = "TextureAtlas/tankTextures.atlas";

    private final AssetManager assetManager = new AssetManager();
    private final ParticleEffectAssetLoader particleAssetLoader = new ParticleEffectAssetLoader(assetManager);
    private final SoundEffectAssetLoader soundAssetLoader = new SoundEffectAssetLoader(assetManager);
    private final SkinAssetLoader skinAssetLoader = new SkinAssetLoader(assetManager);

    public void loadSkin() {
        skinAssetLoader.loadSkin();
    }

    public void loadParticles() {
        particleAssetLoader.loadParticles();
    }

    public void loadTextures() {
        assetManager.load(TEXTURE_ATLAS_PATH, TextureAtlas.class);
    }

    public void loadSounds() {
        soundAssetLoader.loadSounds();
    }

    public ParticleEffect getParticle(ParticleType name) {
        return particleAssetLoader.getParticleEffect(name);
    }

    public Sound getSound(SoundType name) {
        return soundAssetLoader.getSoundEffect(name);
    }

    public Skin getSkin() {
        return skinAssetLoader.getSkin();
    }

    public TextureAtlas getTextureAtlas() {
        return assetManager.get(TEXTURE_ATLAS_PATH, TextureAtlas.class);
    }

    public Font getFont(String fontName, float size) {
        return skinAssetLoader.getFont(fontName, size);
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
