package com.game.code.screens;

import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.code.Application;
import com.game.code.EntityBuilding.Summoners.SummonerType;
import com.game.code.EntityBuilding.battlefiled.BorderPlacer;
import com.game.code.EntityBuilding.battlefiled.SquarePlacer;
import com.game.code.Socket.OnlineGameButtonSystem;
import com.game.code.Socket.SocketOnlineGamesHandler;
import com.game.code.components.ButtonTemplateComponent;
import com.game.code.components.TextComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.BoundedCamera;
import com.game.code.utils.Bounds;
import com.github.tommyettinger.textra.TextraLabel;
import io.socket.client.Socket;

public class OnlineGameListScreen extends EngineScreen {

    private final Bounds bounds = new Bounds(12, 12);
    private final Bounds innerBounds = new Bounds(3, 5, 4, 9);

    public OnlineGameListScreen(Application app) {
        super(app);
    }

    @Override
    public void loaded() {
        super.loaded();
        initSocket();
        createGrid();
    }

    @Override
    protected Viewport initViewport() {
        return new ExtendViewport(9, 6, new BoundedCamera(bounds));
    }

    private void initSocket() {
        OnlineGameButtonSystem buttonController = new OnlineGameButtonSystem(entityBuilder, innerBounds);
        engine.addSystem(buttonController);

        Socket join = app.getSocket("/join");

        new SocketOnlineGamesHandler(join, app, buttonController);

        join.connect();
    }

    private void createGrid() {
        new BorderPlacer(bounds).iterate(spot -> entityCreator.createEntityOn(spot.x, spot.y, "Border"));

        entityCreator.setSummonerType(SummonerType.Sprite);
        new SquarePlacer(bounds).iterate(spot -> entityCreator.createEntityOn(spot.x, spot.y, 2, "plain"));

        entityBuilder.build("Text");
        TextraLabel label = entityBuilder.getComponent(TextComponent.class).label;
        label.setText("List of online games");
        label.setColor(app.skin.getColor("brown"));

        entityBuilder.getComponent(TransformComponent.class).position.set(6, 6);
        engine.addEntity(entityBuilder.getEntity());

        entityCreator.clearSettings();
        entityCreator.createEntityOn(11, 2, "Box");
        entityCreator.createEntityOn(11, 3, "Box");

        entityCreator.clearSettings();
        entityCreator.createEntityOn(1,6, "Bush");
        entityCreator.createEntityOn(1,5, "Bush");
        entityCreator.createEntityOn(1,3, "Bush");
        entityCreator.createEntityOn(11,11, "Bush");
        entityCreator.createEntityOn(4,11, "Bush");
        entityCreator.createEntityOn(10,11, "Bush");

        entityCreator.getSummoningSettings().degAngle = 30;
        entityCreator.createEntityOn(9, 8.5f, "Box");
        entityCreator.getSummoningSettings().degAngle = 0;
        entityCreator.createEntityOn(11, 8, "Box");

        entityCreator.setSummonerType(SummonerType.Button);
        entityCreator.getCreationSettings(ButtonTemplateComponent.class).activateEvent =  () -> app.loadScreen(new MenuScreen(app));
        entityCreator.createEntityOn( 9, 2, -1, "back");

        createTank();
    }

    private void createTank() {
        entityCreator.clearSettings();
        entityCreator.setSummonerType(SummonerType.Tank);
        entityCreator.createEntityOn(6, 6, "player");
    }

    @Override
    public void dispose() {
        super.dispose();

        app.getSocket("/join").disconnect();
    }
}
