package com.game.code.AssetManagment;

import com.badlogic.gdx.assets.AssetManager;

import java.util.HashMap;
import java.util.HashSet;

//needs something from assets
public interface AssetRequest {

    //make requests
    void request(HashMap<String, Class<?>> requests, HashSet<AssetRequest> clients);

    //receive requested assets
    default void passAssets(AssetRequestProcessor assets) {}

    //add request to a buffer
    default void addRequest(HashMap<String, Class<?>> currentRequests, HashSet<AssetRequest> clients, String fileName, Class<?> fileClass, AssetRequest client) {
        if(!currentRequests.containsKey(fileName))
            currentRequests.put(fileName, fileClass);
        clients.add(client);
    }
}
