package com.game.code.Tank.Cab;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class CabData implements Json.Serializable, Cloneable {
    public String type;

    public float invincibilityTime;

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

        invincibilityTime = jsonData.getFloat("invincibilityTime");
        health = jsonData.getFloat("health");
        speed = jsonData.getFloat("speed");
        mobility = jsonData.getFloat("mobility");
    }

    @Override
    public CabData clone() {
        try {
            CabData clone = (CabData) super.clone();
            clone.type = type;
            clone.health = health;
            clone.speed = speed;
            clone.mobility = mobility;
            clone.invincibilityTime = invincibilityTime;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
