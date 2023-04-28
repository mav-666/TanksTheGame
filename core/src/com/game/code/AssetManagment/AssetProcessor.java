package com.game.code.AssetManagment;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.utils.Null;

import java.util.HashMap;
import java.util.HashSet;

public abstract class AssetProcessor {

    public abstract void receiveRequest(String fileName, Class<?> fileClass, @Null AssetRequest client);


    public void load() {
        getRequests().keySet().stream().toList().forEach(
                (fileName) -> loadAsset(applyDirectory(fileName, getRequests().get(fileName)), getRequests().get(fileName))
        );
    }

    public abstract <T> void loadAsset(String fileName, Class<T> clazz);

    public abstract <T> void loadAsset(String fileName, Class<T> clazz, AssetLoaderParameters<T> parameters);

    protected String applyDirectory(String fileName, Class<?> clazz) {
        return clazz.getSimpleName() +"/"+ fileName;
    }


    public abstract void update();

    public abstract float getPercent();

    public abstract boolean isFinished();


    public void sendAllAssets() {
        getClients().forEach(this::sendAsset);
        getClients().clear();
    }

    protected abstract void sendAsset(AssetRequest assetRequest);


    public abstract <T> T get(String fileName, Class<T> clazz);


    protected abstract HashMap<String, Class<?>> getRequests();

    protected abstract HashSet<AssetRequest> getClients();

    protected abstract <T> void setLoader(Class<T> clazz, AssetLoader<T, ?> loader);

    protected abstract FileHandleResolver getResolver();

}
