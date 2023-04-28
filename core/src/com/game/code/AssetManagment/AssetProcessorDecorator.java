package com.game.code.AssetManagment;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;

import java.util.HashMap;
import java.util.HashSet;

public abstract class AssetProcessorDecorator extends AssetProcessor {
    AssetProcessor assetProcessor;

    protected AssetProcessorDecorator(AssetProcessor assetProcessor) {
        this.assetProcessor = assetProcessor;
    }

    @Override
    public void receiveRequest(String fileName, Class<?> fileClass, AssetRequest client) {
        assetProcessor.receiveRequest(fileName, fileClass, client);
    }


    @Override
    public <T> void loadAsset(String fileName, Class<T> clazz) {
        assetProcessor.loadAsset(fileName,clazz);
    }

    @Override
    public <T> void loadAsset(String fileName, Class<T> clazz, AssetLoaderParameters<T> parameters) {
        assetProcessor.loadAsset(fileName, clazz, parameters);
    }


    @Override
    public void update() {
        assetProcessor.update();
    }

    @Override
    public float getPercent() {
        return assetProcessor.getPercent();
    }

    @Override
    public boolean isFinished() {
        return assetProcessor.isFinished();
    }

    @Override
    protected void sendAsset(AssetRequest assetRequest) {
        assetProcessor.sendAsset(assetRequest);
    }

    @Override
    public <T> T get(String fileName, Class<T> clazz) {
        return assetProcessor.get(fileName, clazz);
    }


    @Override
    public HashMap<String, Class<?>> getRequests() {
        return assetProcessor.getRequests();
    }

    @Override
    protected HashSet<AssetRequest> getClients() {
        return assetProcessor.getClients();
    }

    @Override
    protected <T> void setLoader(Class<T> clazz, AssetLoader<T, ?> loader) {
        assetProcessor.setLoader(clazz, loader);
    }

    @Override
    protected FileHandleResolver getResolver() {
        return assetProcessor.getResolver();
    }
}
