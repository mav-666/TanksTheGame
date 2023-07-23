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


public class Application extends Game {
    public int seed = 1;
    public SpriteBatch batch;
    public Assets assets = new Assets();
    public JsonLoader jsonLoader = new InternalJsonLoader();
    public Skin skin;

    private final ClientSocket clientSocket = new ClientSocket();

    private final ScreenHistory screenHistory = new ScreenHistory(1);
    private final ScreenLoader screenLoader = new ScreenLoader(this);

    @Override
    public void create() {
        screenHistory.addFilter((screen) -> screen instanceof AbstractLoadingScreen);

        assets.loadSkin();
        skin = assets.getSkin();

        batch = new SpriteBatch();

        initTween();

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

    public Socket getSocket() {
        return clientSocket.getSocket();
    }
}

