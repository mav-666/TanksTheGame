package com.game.code.screens.Loading;

import com.badlogic.gdx.assets.AssetManager;

public class AssetTaskLoader implements TaskLoader {
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
        return assetManager.isFinished() || assetManager.getProgress() == 1f;
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
