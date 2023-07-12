package com.game.code.Socket;

import com.badlogic.gdx.utils.JsonValue;
import com.game.code.EntityBuilding.Json.CachedLoader;
import io.socket.client.Ack;
import io.socket.client.Socket;
import org.json.JSONObject;

import java.util.Collections;

public class SocketJsonLoader extends CachedLoader {

    private Socket socket;
    private JsonValue loadedJson;

    SocketJsonLoader(Socket socket) {
        this.socket = socket;
    }

    @Override
    protected JsonValue loadFromSource(String name) {
        socket.emit("GetEntityJson", new JSONObject(Collections.singletonMap("name", name)), (Ack) args ->{
            JSONObject data = ((JSONObject) args[0]);
            loadedJson = convert(data);
        });
        return loadedJson;
    }

    private JsonValue convert(JSONObject jsonObject) {
        return new JsonValue(jsonObject.toString());
    }
}
