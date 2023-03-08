package com.game.code.AssetManagment;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;

public interface AssetStrategy<T> {

    Class<T> getType();
    AssetLoaderParameters<T> getParameter();

    void respondOnRequest(AssetRequest assetRequest);



}
