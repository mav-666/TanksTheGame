package com.game.code.Tank;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class CabData implements Json.Serializable {
    public String type;

    public long invincibilityTime;

    public float health;
    public float speed;
    public float mobility;

    @Override
    public void write(Json json) {
        json.writeValue("type", type);

        json.writeValue("invincibilityTime", invincibilityTime);
        json.writeValue("health", health);
        json.writeValue("speed", speed);
        json.writeValue("mobility", mobility);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        type = jsonData.getString("type");

        invincibilityTime = jsonData.getLong("invincibilityTime");
        health = jsonData.getFloat("health");
        speed = jsonData.getFloat("speed");
        mobility = jsonData.getFloat("mobility");
    }
}
