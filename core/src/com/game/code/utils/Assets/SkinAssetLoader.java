package com.game.code.utils.Assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.systems.Render.RenderingSystem;
import com.github.tommyettinger.textra.Font;
import com.ray3k.stripe.FreeTypeSkinLoader;

public class SkinAssetLoader {
    private final static String SKIN_PATH = "Skin/skin.json";
    private final static String SKIN_ATLAS_PATH = "Skin/skin.atlas";

    private final ObjectMap<String, Font> fonts= new ObjectMap<>();
    private final AssetManager assetManager;
    
    SkinAssetLoader(AssetManager assetManager) {
        assetManager.setLoader(Skin.class, new FreeTypeSkinLoader(assetManager.getFileHandleResolver()));

        this.assetManager = assetManager;
    }

    public void loadSkin() {
        SkinLoader.SkinParameter params = new SkinLoader.SkinParameter(SKIN_ATLAS_PATH);
        assetManager.load(SKIN_PATH, Skin.class, params);
        assetManager.finishLoading();
    }
    
    public Skin getSkin() {
        return assetManager.get(SKIN_PATH, Skin.class);
    }

    public Font getFont(String fontName, float size) {
        if(!containsFont(fontName + size))
            addFont(fontName, size);

        return fonts.get(fontName + size);
    }

    public void addFont(String fontName, float size) {
        fonts.put(fontName + size, new Font(getSkin().getFont(fontName)).scaleTo(RenderingSystem.toMeters(size), RenderingSystem.toMeters(size)));
    }

    public boolean containsFont(String fontName) {
        return fonts.containsKey(fontName);
    }
}