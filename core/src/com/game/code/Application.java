package com.game.code;


import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.FieldInitializers.AssetParticleInitializer;
import com.game.code.EntityBuilding.FieldInitializers.AssetTextureInitializer;
import com.game.code.EntityBuilding.FieldInitializers.SkinColorInitializer;
import com.game.code.EntityBuilding.FieldInitializers.TextraLabelInitializer;
import com.game.code.EntityBuilding.Json.JsonLoader;
import com.game.code.FileManagment.InternalJsonLoader;
import com.game.code.Socket.ClientSocket;
import com.game.code.screens.GameLobbyScreen;
import com.game.code.screens.Loading.ScreenLoader;
import com.game.code.screens.MenuScreen;
import com.game.code.utils.Assets.Assets;
import com.game.code.utils.TweenUtils.BodyTransformAccessor;
import com.game.code.utils.TweenUtils.ColorAccessor;
import com.game.code.utils.TweenUtils.Vector2Accessor;
import io.socket.client.Socket;


public class Application extends Game {
    public SpriteBatch batch;
    public Assets assets = new Assets();
    public JsonLoader jsonLoader = new InternalJsonLoader();
    public ComponentInitializer componentInitializer = new ComponentInitializer();
    public Skin skin;

    private final ClientSocket clientSocket = new ClientSocket();

    private final ScreenLoader screenLoader = new ScreenLoader(this);

    @Override
    public void create() {
        assets.loadSkin();
        skin = assets.getSkin();

        batch = new SpriteBatch();

        initTween();

        initComponentInitializer();

        clientSocket.getSocket().connect();

        loadScreen(new MenuScreen(this));
    }

    private void initTween() {
        Tween.registerAccessor(Color.class, new ColorAccessor());
        Tween.registerAccessor(Vector2.class, new Vector2Accessor());
        Tween.registerAccessor(Body.class, new BodyTransformAccessor());
    }

    private void initComponentInitializer() {
        componentInitializer.addInitializer(new AssetTextureInitializer(assets));
        componentInitializer.addInitializer(new AssetParticleInitializer(assets));

        componentInitializer.addInitializer(new TextraLabelInitializer(assets));
        componentInitializer.addInitializer(new SkinColorInitializer(skin));
    }

    @Override
    public void setScreen(Screen screen) {
        if(getScreen() != null && !(getScreen() instanceof GameLobbyScreen))
            screenLoader.disposeScreen(getScreen());
        super.setScreen(screen);
    }

    @Override
    public void render() {
        super.render();

        screenLoader.updateScreen();
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

    public Socket getSocket() {
        return clientSocket.getSocket();
    }

    public Socket getSocket(String nsp) {
        return clientSocket.getSocket(nsp);
    }
}

