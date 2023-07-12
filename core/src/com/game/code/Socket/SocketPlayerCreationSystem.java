package com.game.code.Socket;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.game.code.EntityBuilding.battlefiled.PlayerCreator;
import io.socket.client.Socket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketPlayerCreationSystem extends EntitySystem {

    private final Array<String> creationQueue = new Array<>();
    private final PlayerCreator playerCreator;

    public SocketPlayerCreationSystem(Socket socket, PlayerCreator playerCreator) {
        this.playerCreator = playerCreator;

        socket.on("playerJoined", args -> {
            JSONObject data = (JSONObject) args[0];

            try {
                String id = data.getString("id");
                creationQueue.add(id);
            } catch (JSONException e) {
                Gdx.app.log("SocketCreation", "Failed reading id");
            }

        }).on("getPlayers", args -> {
            JSONArray dataArray = (JSONArray) args[0];
            for(int i = 0; i < dataArray.length(); i++) {
                try {
                    creationQueue.add(dataArray.getJSONObject(i).getString( "id"));
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
        playerCreator.create(id);
        creationQueue.removeValue(id, false);
    }
}
