package com.game.code.screens;

import com.badlogic.gdx.Gdx;
import com.game.code.Application;
import com.game.code.Socket.SocketShootingInputSystem;
import io.socket.client.Socket;
import org.json.JSONObject;

import java.util.Map;

public class JoinScreen extends GameLobbyScreen {

    public JoinScreen(Application app, String roomId) {
        super(app);

        this.roomId = roomId;
    }

    @Override
    protected void initSocket() {
        super.initSocket();

        engine.getSystem(SocketShootingInputSystem.class).setProcessing(false);

        Socket room = app.getSocket("/inRoom");
        room.on(Socket.EVENT_CONNECT, args -> room.emit("joinRoom",  new JSONObject(Map.of("roomId", roomId))));
        room.connect();
    }

    protected void logPlayerEvent(Object... args) {
        String playerName = Gdx.app.getPreferences("Prefs").getString("playerName");
        app.getSocket("/inRoom").emit("playerLogged", new JSONObject(Map.of("name", playerName, "disabled", true)));
    }
}
