package com.game.code.Socket;

import com.badlogic.gdx.Gdx;
import com.game.code.Application;
import com.game.code.screens.JoinScreen;
import com.game.code.utils.Event;
import io.socket.client.Ack;
import io.socket.client.Socket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class SocketOnlineGamesHandler {

    private final Socket socket;

    private final Application app;

    private final OnlineGameButtonController buttonController;

    public SocketOnlineGamesHandler(Socket socket, Application app, OnlineGameButtonController buttonController) {
        this.socket = socket;
        this.app = app;
        this.buttonController = buttonController;
        socket.on("getOnlineGames", this::getOnlineGamesEvent)
                .on("newOnlineGame", this::newOnlineGameEvent)
                .on("gameClosed", this::gameUpdated)
                .on("gameStarted", this::gameUpdated)
                .on("gameEnded", this::gameUpdated);
    }

    private void getOnlineGamesEvent(Object... args) {
        JSONArray dataArray = (JSONArray) args[0];
        try {

            for(int i = 0; i < dataArray.length(); i++)
                updateGame(parseOnlineGameData(dataArray.getJSONObject(i), true));


        } catch (JSONException e) {
            Gdx.app.log("SocketOnlineGameError", "Failed reading games array");
        }
    }

    private OnlineGameData parseOnlineGameData(JSONObject data, boolean isNew) {
        try {
            String id = data.getString("id");
            String name = data.getString("gameName");
            OnlineGameData.State state = OnlineGameData.State.valueOf(data.getString("state"));
            Event event = isNew ? createJoinRequestEvent(id) : () -> {};

            return new OnlineGameData(id, name, state, event);

        } catch (JSONException e) {
            Gdx.app.log("SocketOnlineGameError", "Failed reading onlineGameData");
            return new OnlineGameData("error", "error", OnlineGameData.State.CLOSED, () -> {});
        }
    }

    private Event createJoinRequestEvent(String id) {
        return () -> socket.emit("joinGame", new JSONObject(Map.of("id", id)), (Ack) this::joinRespond);
    }

    private void joinRespond(Object[] args) {
        try {
            JSONObject data = (JSONObject) args[0];
            boolean permitted = data.getBoolean("permitted");

            if(permitted) {
                String roomId = data.getString("roomId");
                changeScreen(roomId);
            }
        } catch (JSONException e) {
            Gdx.app.log("SocketOnlineGameError", "Failed reading permission");
        }
    }

    private void changeScreen(String roomId) {
         app.loadScreen(new JoinScreen(app, roomId));
    }

    private void updateGame(OnlineGameData onlineGameData) {
        buttonController.updateButtonWith(onlineGameData);
    }

    private void newOnlineGameEvent(Object... args) {
        updateGame(parseOnlineGameData((JSONObject) args[0], true));
    }

    private void gameUpdated(Object... args) {
        JSONObject data = (JSONObject) args[0];
        updateGame(parseOnlineGameData(data, false));
    }
}
