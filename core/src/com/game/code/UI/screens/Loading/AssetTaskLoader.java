package com.game.code.UI.screens.Loading;

import com.badlogic.gdx.assets.AssetManager;

public class AssetTaskLoader extends TaskLoader {
    AssetManager assetManager;

    protected AssetTaskLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public void update() {
        assetManager.update();
    }

    @Override
    public boolean isDone() {
        return assetManager.isFinished();
    }

    @Override
    public float getProgress() {
        return assetManager.getProgress();
    }

    @Override
    public String getName() {
        return "assets";
    }
}
