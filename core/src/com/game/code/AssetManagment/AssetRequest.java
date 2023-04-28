package com.game.code.AssetManagment;

//needs something from assets
public interface AssetRequest {

    //make requests
    void request(AssetProcessor assetRequestProcessor);

    //receive requested assets
    void passAssets(AssetProcessor assets);

}
