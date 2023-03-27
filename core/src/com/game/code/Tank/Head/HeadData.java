package com.game.code.Tank.Head;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class HeadData implements Json.Serializable {
    public String type;

    public float rotationSpeed;

    @Override
    public void write(Json json) {
        json.writeValue("type", type);
        json.writeValue("rotationSpeed", rotationSpeed);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        type = jsonData.getString("type");
        rotationSpeed = jsonData.getFloat("rotationSpeed");
    }
}
