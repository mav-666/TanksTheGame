package com.game.code.AssetManagment;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

//Handles assetRequests and loads required assets via assetManager
public class AssetRequestProcessor implements Disposable {
    protected final AssetManager assetManager;
    //All requests to be loaded
    private final  HashMap<String, Class<?>> requests;

    protected final HashSet<AssetRequest> clients;

    protected HashSet<AssetStrategy<?>> assetStrategies;

    public AssetRequestProcessor(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.requests = new HashMap<>();
        this.clients = new HashSet<>();
        this.assetStrategies = new HashSet<>();
    }
    //receive requests
    public void receiveRequest(AssetRequest assetRequest) {
        assetRequest.request(requests, clients);
    }

    //load after all requests are obtained
    public void load() {
        String[] fileNames = requests.keySet().toArray(new String[]{});

        for (String fileName : fileNames) {
            Class<?> clazz= requests.get(fileName);
            loadAsset(applyDirectory(fileName, clazz), clazz);
        }
    }

    private String applyDirectory(String fileName, Class<?> clazz) {
        return clazz.getSimpleName() +"/"+ fileName;
    }

    public void update() {
        assetManager.update();
    }

    public boolean isFinished() {
        if(assetManager.isFinished()) {
            update();
            assetManager.finishLoading();
            sendAllAssets();
        }


        return assetManager.isFinished();

    }
    //give assets to clients//give assets to clients
    protected void sendAllAssets() {
        clients.forEach(client -> {
            assetStrategies.forEach((strategy) -> strategy.respondOnRequest(this, client));
            client.passAssets(this);
        });
    }

    public <T> T get(String fileName, Class<T> clazz) {
        return assetManager.get(applyDirectory(fileName, clazz), clazz);
    }

    //load single asset
    protected void loadAsset(String fileName, Class<?> clazz) {
        Optional<AssetStrategy<?>> foundStrategy = assetStrategies.stream().filter(((strategy) -> strategy.getType() == clazz)).findFirst();

        if(foundStrategy.isPresent()) {
            loadWithParameters(fileName, foundStrategy.get());
            return;
        }
        assetManager.load(fileName, clazz);


    }
    private <T> void loadWithParameters(String fileName, AssetStrategy<T> assetStrategy) {
        assetManager.load(fileName, assetStrategy.getType(), assetStrategy.getParameter());
    }

    public void addAssetStrategy(AssetStrategy<?> assetStrategy) {
        assetStrategies.add(assetStrategy);
    }

    @Override
    public void dispose() {
        assetStrategies.forEach(Disposable::dispose);
        assetManager.dispose();
    }
}
