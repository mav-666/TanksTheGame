package com.game.code.AssetManagment;

import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ray3k.stripe.FreeTypeSkinLoader;

public class SkinApplier extends AssetProcessorDecorator {
    private final SkinLoader.SkinParameter parameter;

    public SkinApplier(AssetProcessor assetProcessor) {
        super(assetProcessor);
        setLoader(Skin.class, new FreeTypeSkinLoader(getResolver()));

        parameter = new SkinLoader.SkinParameter("Skin/skin.atlas");
    }

    @Override
    public <T> void loadAsset(String fileName, Class<T> clazz) {
        if(clazz == Skin.class) {
            loadAsset(fileName, Skin.class, parameter);
            return;
        }
        super.loadAsset(fileName, clazz);
    }
}
