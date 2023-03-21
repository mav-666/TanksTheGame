package com.game.code.AssetManagment;

import com.badlogic.gdx.assets.AssetManager;

import java.util.HashMap;
import java.util.HashSet;

//needs something from assets
public interface AssetRequest {

    //make requests
    void request(AssetRequestProcessor assetRequestProcessor);

    //receive requested assets
    void passAssets(AssetRequestProcessor assets);

}
