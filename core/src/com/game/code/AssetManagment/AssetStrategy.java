package com.game.code.AssetManagment;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.utils.Disposable;

public interface AssetStrategy<T> extends Disposable {

    Class<T> getType();
    AssetLoaderParameters<T> getParameter();

    void respondOnRequest(AssetRequestProcessor assetRequestProcessor, AssetRequest assetRequest);

}
