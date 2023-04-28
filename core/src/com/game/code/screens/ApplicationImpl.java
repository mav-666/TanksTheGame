package com.game.code.screens;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;	
import com.badlogic.gdx.utils.Disposable;
import com.game.code.AssetManagment.*;
import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ApplicationImpl extends Game implements Application {
	private SpriteBatch batch;
	private AssetProcessor assetProcessor;

	private ScreenHistory screenHistory;
	private ScreenLoader screenLoader;

	@Override
	public void create() {
		batch = new SpriteBatch();

		screenHistory = new ScreenHistory(50);
		screenHistory.addFilter((screen) -> screen instanceof LoadingScreen);

		screenLoader = new ScreenLoader(this, new LoadingScreenFactory(this));

		assetProcessor = new ParticlePoolApplier(new SkinApplier(new AssetRequestProcessor(new AssetManager())));

		loadScreen(new TitleScreen(this));
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);

		screenHistory.add(screen);
	}

	@Override
	public void dispose(){
		if( assetProcessor instanceof Disposable)
			((Disposable) assetProcessor).dispose();
		getScreen().dispose();
		batch.dispose();
	}

	@Override
	public void loadScreen(Screen screen) {
		screenLoader.loadScreen(screen);
	}

	@Override
	public Screen getPreviousScreen() {
		return screenHistory.getPreviousScreen();
	}

	@Override
	public SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public AssetProcessor getAssetProcessor() {
		return assetProcessor;
	}
}

