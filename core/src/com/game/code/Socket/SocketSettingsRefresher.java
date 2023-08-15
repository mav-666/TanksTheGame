package com.game.code.Socket;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.UI.Meter;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketSettingsRefresher {

    private final ObjectMap<String, Meter> namedSettings = new ObjectMap<>();

    public SocketSettingsRefresher(Socket socket) {
        socket.on("settingChanged", this::settingChangedEvent);
    }

    private void settingChangedEvent(Object... args) {
        try{
            JSONObject data = (JSONObject) args[0];

            String name = data.getString("name");
            float value = (float) data.getDouble("value");

            namedSettings.get(name).setValue(value);

        } catch(JSONException e) {
            Gdx.app.log("SocketSettingsError", "Failed reading setting name");
        }
    }

    public void addSetting(String name, Meter meter) {
        namedSettings.put(name, meter);
    }
}
