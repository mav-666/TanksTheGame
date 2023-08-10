package com.game.code.Socket;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.FieldInitializers.TankConfig.TankConfigInitializer;
import com.game.code.EntityBuilding.Json.TankConfigJsonFactory;
import com.game.code.EntityBuilding.battlefiled.PlayerCreator;
import io.socket.client.Socket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketPlayerCreationSystem extends EntitySystem {

    private final Socket socket;
    private final JSONObject tankData;
    private final Array<String> creationQueue = new Array<>();
    private final PlayerCreator playerCreator;

    public SocketPlayerCreationSystem(Socket socket, PlayerCreator playerCreator) {
        this.playerCreator = playerCreator;

        this.socket = socket;
        this.tankData = new JSONObject();

        ComponentInitializer.getInstance().addInitializer(
                new TankConfigInitializer(
                        new TankConfigJsonFactory(
                                new SocketJsonLoader(tankData))));

        socket.on("playerJoined", this::playerJoinedEvent)
                .on("getPlayers", this::getPlayersEvent);
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

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(!creationQueue.isEmpty())
            creationQueue.forEach(this::createPlayer);
    }

    public void createPlayer(String id) {
        playerCreator.create(id);
        creationQueue.removeValue(id, false);
    }
}
