package com.game.code.AssetManagment;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.HashSet;

//Handles assetRequests like a proxy for assetManager
public class AssetRequestProcessor extends AssetProcessor implements Disposable {
    protected final AssetManager assetManager;
    //All requests to be loaded
    private final  HashMap<String, Class<?>> requests;

    protected final HashSet<AssetRequest> clients;

    public AssetRequestProcessor(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.requests = new HashMap<>();
        this.clients = new HashSet<>();
    }

    //receive requests
    public void receiveRequest(String fileName, Class<?> fileClass, AssetRequest client) {
        if(!requests.containsKey(fileName))
            requests.put(fileName, fileClass);
        if(client != null) clients.add(client);
    }

    @Override
    public <T> void loadAsset(String fileName, Class<T> clazz) {
        assetManager.load(fileName, clazz);
    }

    @Override
    public <T> void loadAsset(String fileName, Class<T> clazz, AssetLoaderParameters<T> parameters) {
        assetManager.load(fileName, clazz, parameters);
    }

    @Override
    public void update() {
        assetManager.update();
    }

    @Override
    public float getPercent() {
        return assetManager.getProgress();
    }

    @Override
    public boolean isFinished() {
        return assetManager.isFinished();
    }

    @Override
    protected void sendAsset(AssetRequest assetRequest) {
        assetRequest.passAssets(this);
    }

    @Override
    public HashMap<String, Class<?>> getRequests() {
        return requests;
    }

    @Override
    protected HashSet<AssetRequest> getClients() {
        return clients;
    }

    @Override
    public <T> T get(String fileName, Class<T> clazz) {
        return assetManager.get(applyDirectory(fileName, clazz), clazz);
    }

    protected <T> void setLoader(Class<T> clazz, AssetLoader<T, ?> loader) {
        assetManager.setLoader(clazz, loader);
    }

    @Override
    protected FileHandleResolver getResolver() {
        return assetManager.getFileHandleResolver();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
