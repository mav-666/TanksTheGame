package com.game.code;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.game.code.Socket.ClientSocket;
import com.game.code.UI.screens.AbstractLoadingScreen;
import com.game.code.UI.screens.Loading.ScreenHistory;
import com.game.code.UI.screens.Loading.ScreenLoader;
import com.game.code.utils.Assets;
import io.socket.client.Socket;

public class Application extends Game {
    public SpriteBatch batch;
    public Skin skin;
    public Assets assets;

    private ClientSocket clientSocket;
    private ScreenHistory screenHistory;
    private ScreenLoader screenLoader;


    @Override
    public void create() {
        batch = new SpriteBatch();

        screenHistory = new ScreenHistory(5);
        screenHistory.addFilter((screen) -> screen instanceof AbstractLoadingScreen);

        screenLoader = new ScreenLoader(this);

        assets = new Assets();

        assets.loadUISkin();

        skin = assets.getSkin();

        clientSocket = new ClientSocket();

        loadScreen(new GameScreen(this));


    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);

        screenHistory.add(screen);
    }

    @Override
    public void dispose(){
        clientSocket.getSocket().disconnect();
        assets.dispose();
        getScreen().dispose();
        batch.dispose();
    }

    public void loadScreen(Screen screen) {
        screenLoader.loadScreen(screen);
    }

    public Screen getPreviousScreen() {
        return screenHistory.getPreviousScreen();
    }

    public ClientSocket getClientSocket() {
        return clientSocket;
    }
}

