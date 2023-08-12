package com.game.code;


import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.game.code.EntityBuilding.Json.JsonLoader;
import com.game.code.FileManagment.InternalJsonLoader;
import com.game.code.Socket.ClientSocket;
import com.game.code.screens.AbstractLoadingScreen;
import com.game.code.screens.Loading.ScreenHistory;
import com.game.code.screens.Loading.ScreenLoader;
import com.game.code.screens.MenuScreen;
import com.game.code.utils.Assets.Assets;
import com.game.code.utils.TweenUtils.BodyTransformAccessor;
import com.game.code.utils.TweenUtils.ColorAccessor;
import com.game.code.utils.TweenUtils.Vector2Accessor;
import io.socket.client.Socket;

import java.util.Optional;


public class Application extends Game {
    public int seed = 1;
    public SpriteBatch batch;
    public Assets assets = new Assets();
    public JsonLoader jsonLoader = new InternalJsonLoader();
    public Skin skin;

    private final ClientSocket clientSocket = new ClientSocket();

    private final ScreenHistory screenHistory = new ScreenHistory(5);
    private final ScreenLoader screenLoader = new ScreenLoader(this, screenHistory);

    @Override
    public void create() {
        screenHistory.addFilter((screen) -> screen instanceof AbstractLoadingScreen);

        assets.loadSkin();
        skin = assets.getSkin();

        batch = new SpriteBatch();

        initTween();

        clientSocket.getSocket().connect();

        loadScreen(new MenuScreen(this));
    }

    private void initTween() {
        Tween.registerAccessor(Color.class, new ColorAccessor());
        Tween.registerAccessor(Vector2.class, new Vector2Accessor());
        Tween.registerAccessor(Body.class, new BodyTransformAccessor());
    }

    @Override
    public void render() {
        super.render();

        screenLoader.updateScreen();
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);

        if(!screenHistory.containsScreen(screen))
            screenHistory.add(screen);
    }

    @Override
    public void dispose(){
        clientSocket.disconnectAll();
        assets.dispose();
        getScreen().dispose();
        batch.dispose();
    }

    public void loadScreen(Screen screen) {
        screenLoader.loadScreen(screen);
    }

    public Optional<Screen> getScreen(Class<? extends Screen> screenType) {
        return screenHistory.getScreen(screenType);
    }

    public Socket getSocket() {
        return clientSocket.getSocket();
    }

    public Socket getSocket(String nsp) {
        return clientSocket.getSocket(nsp);
    }
}

