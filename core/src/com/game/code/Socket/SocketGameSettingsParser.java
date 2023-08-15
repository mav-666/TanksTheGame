package com.game.code.Socket;

import com.badlogic.gdx.Gdx;
import com.game.code.EntityBuilding.battlefiled.GameSettings;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketGameSettingsParser {

    private final GameSettings gameSettings;

    public SocketGameSettingsParser(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    public void parseGameSettingsData(JSONObject data) {
        setPercentageOf("bush", data);
        setPercentageOf("gasoline", data);
        setPercentageOf("box", data);

        setOtherSettings(data);
    }

    private void setPercentageOf(String fillingName, JSONObject data) {
        try{
            gameSettings.fillingPercentages().put(fillingName, (float) data.getInt(fillingName));
        } catch (JSONException e) {
            gameSettings.fillingPercentages().put(fillingName, 0);
        }
    }

    private void setOtherSettings(JSONObject data) {
        int width = 15;
        int height = 15;

        try{
            width = data.getInt("width");
            height = data.getInt("height");

        } catch (JSONException e) {
            Gdx.app.log("GameSettingsError", "failed setting size");
        } finally {
            gameSettings.otherSettings().put("width", width);
            gameSettings.otherSettings().put("height", height);
        }
    }
}
