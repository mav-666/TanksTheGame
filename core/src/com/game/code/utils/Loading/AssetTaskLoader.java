package com.game.code.utils.Loading;

import com.game.code.AssetManagment.AssetProcessor;

public class AssetTaskLoader implements LoadingTask {
    AssetProcessor assetProcessor;

    public AssetTaskLoader(AssetProcessor assetProcessor) {
        this.assetProcessor = assetProcessor;
    }

    @Override
    public void update() throws LoadingException {
        assetProcessor.update();
    }

    @Override
    public boolean isDone() {
        if(assetProcessor.isFinished())
            assetProcessor.sendAllAssets();
        return assetProcessor.isFinished();
    }

    @Override
    public float getPercent() {
        return assetProcessor.getPercent();
    }

    @Override
    public String getName() {
        return "assets";
    }
}
