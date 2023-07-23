package com.game.code.Socket;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.EntityBuilding.battlefiled.PlayerCreator;
import io.socket.client.Socket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketPlayerCreationSystem extends EntitySystem {

    private final ObjectMap<String, String> playerNames = new ObjectMap<>();
    private final Array<String> creationQueue = new Array<>();
    private final PlayerCreator playerCreator;

    public SocketPlayerCreationSystem(Socket socket, String playerName, PlayerCreator playerCreator) {
        playerNames.put("player", playerName);
        this.playerCreator = playerCreator;

        socket.on("playerJoined", args -> {
            JSONObject data = (JSONObject) args[0];

            try {
                String id = data.getString("id");
                String name = data.getString("name");
                playerNames.put(id, name);
                creationQueue.add(id);
            } catch (JSONException e) {
                Gdx.app.log("SocketCreation", "Failed reading id");
            }

        }).on("getPlayers", args -> {
            JSONArray dataArray = (JSONArray) args[0];
            for(int i = 0; i < dataArray.length(); i++) {
                try {
                    JSONObject data = dataArray.getJSONObject(i);
                    String id = data.getString("id");
                    String name = data.getString("name");

                    playerNames.put(id, name);
                    creationQueue.add(id);
                } catch (JSONException e) {
                    Gdx.app.log("SocketCreation", "Failed reading id");
                }
            }
            creationQueue.add("player");
        });
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(!creationQueue.isEmpty())
            creationQueue.forEach(this::createPlayer);
    }

    public void createPlayer(String id) {
        playerCreator.create(id + " " + playerNames.get(id));
        creationQueue.removeValue(id, false);
    }
}
