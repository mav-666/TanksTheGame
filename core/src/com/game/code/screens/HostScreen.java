package com.game.code.screens;

import com.badlogic.gdx.Gdx;
import com.game.code.Application;
import io.socket.client.Ack;
import io.socket.client.Socket;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Map;

public class HostScreen extends GameLobbyScreen {

    public HostScreen(Application app) {
        super(app);
    }

    @Override
    protected void initSocket() {
        super.initSocket();
        Socket host = app.getSocket("/host");

        host.on(Socket.EVENT_CONNECT, args -> host.emit("hostGame",
                new JSONObject(Collections.singletonMap("gameName", Gdx.app.getPreferences("prefs").getString("playerName")))
                ,(Ack) (argsAck) -> {
                    Socket room = app.getSocket("/inRoom");
                    roomId = host.id();
                    room.on(Socket.EVENT_CONNECT, argsR -> room.emit("joinRoom",  new JSONObject(Map.of("roomId", host.id()))));
                    room.connect();
                }));
        host.connect();
    }

    protected void logPlayerEvent(Object... args) {
        String playerName = Gdx.app.getPreferences("Prefs").getString("playerName") + " host";
        app.getSocket("/inRoom").emit("playerLogged", new JSONObject(Map.of("name", playerName)));
    }
}
