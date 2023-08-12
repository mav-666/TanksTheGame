package com.game.code.Socket;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.game.code.components.IdComponent;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketPlayerRemoveSystem extends EntitySystem implements EntityListener {

    SocketPlayerRemoveSystem(Socket socket) {
        socket.on("playerLeft", this::playerLeftEvent);
    }

    private void playerLeftEvent(Object... args) {
        try {
            String id = ((JSONObject) args[0]).getString("id");


        } catch (JSONException e) {
            Gdx.app.log("SocketCreation", "Failed removing player");
        }
    }


    @Override
    public void entityAdded(Entity entity) {

    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
