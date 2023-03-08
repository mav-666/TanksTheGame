package com.game.code;


import com.game.code.screens.GameScreen;
import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Application extends Game {

	public SpriteBatch batch;

	@Override
	public void create() {
		batch= new SpriteBatch();

		setScreen(new GameScreen(this));
	}

	@Override
	public void dispose(){
		getScreen().dispose();
		batch.dispose();
	}
}

