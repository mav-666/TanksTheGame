package com.game.code.Socket;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.FieldInitializers.TankConfig.TankConfigInitializer;
import com.game.code.EntityBuilding.Json.TankConfigJsonFactory;
import com.game.code.EntityBuilding.battlefiled.PlayerCreator;
import com.game.code.components.DestroyedComponent;
import com.game.code.components.IdComponent;
import com.game.code.utils.DeactivatingSystem;
import com.game.code.utils.Mappers;
import io.socket.client.Socket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketPlayerCreationSystem extends DeactivatingSystem {

    private final Socket socket;
    private final JSONObject tankData;
    private final ComponentInitializer componentInitializer;
    private final Array<String> creationQueue = new Array<>();
    private final Array<String> removalQueue = new Array<>();
    private final PlayerCreator playerCreator;

    public SocketPlayerCreationSystem(Socket socket, PlayerCreator playerCreator, ComponentInitializer componentInitializer) {
        super();
        this.playerCreator = playerCreator;

        this.socket = socket;
        this.tankData = new JSONObject();
        this.componentInitializer = componentInitializer;

        activate();
    }

    private void playerJoinedEvent(Object... args) {
        try {
            addPlayerBy((JSONObject) args[0]);
        } catch (JSONException e) {
            Gdx.app.log("SocketCreation", "Failed adding joined player");
        }
    }

    private void getPlayersEvent(Object... args) {
        JSONArray dataArray = (JSONArray) args[0];
        try {

            for(int i = 0; i < dataArray.length(); i++)
                addPlayerBy(dataArray.getJSONObject(i));


        } catch (JSONException e) {
            Gdx.app.log("SocketCreation", "Failed getting current players");
        }
    }

    private void addPlayerBy(JSONObject data) throws JSONException {
            String name = data.getString("name");
            String id = data.getString("id");
            JSONObject tankConfig = data.getJSONObject("tankConfig");

            tankData.put(id, tankConfig);
            creationQueue.add(id + " " + name + (id.equals(socket.id()) ? " player" : ""));
    }

    private void playerLeftEvent(Object... args) {
        try {
            String id = ((JSONObject) args[0]).getString("id");

            removalQueue.add(id);

        } catch (JSONException e) {
            Gdx.app.log("SocketCreation", "Failed removing player");
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(!creationQueue.isEmpty())
            creationQueue.forEach(this::createPlayer);

        if(!removalQueue.isEmpty())
            removalQueue.forEach(this::removePlayer);
    }

    public void createPlayer(String id) {
        playerCreator.create(id);
        creationQueue.removeValue(id, false);
    }

    public void removePlayer(String id) {
        getEngine().getEntitiesFor(Family.all(IdComponent.class).get()).forEach(entity -> {
            if(Mappers.getInstance().get(IdComponent.class, entity).id.equals(id))
                entity.add(getEngine().createComponent(DestroyedComponent.class));
        });
    }

    @Override
    public void removedFromEngine(Engine engine) {
        deactivate();
    }

    @Override
    public void activate() {
        componentInitializer.addInitializer(
                new TankConfigInitializer(
                        new TankConfigJsonFactory(
                                new SocketJsonLoader(tankData))));

        socket.on("playerJoined", this::playerJoinedEvent)
                .on("getPlayers", this::getPlayersEvent)
                .on("playerLeft", this::playerLeftEvent);
    }

    @Override
    public void deactivate() {
        socket.off("playerJoined");
        socket.off("getPlayers");
        socket.off("playerLeft");
    }
}

