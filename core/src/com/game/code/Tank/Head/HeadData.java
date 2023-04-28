package com.game.code.Tank.Head;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class HeadData implements Json.Serializable, Cloneable {
    public String type;

    public float rotationSpeed;
    public float recoil;
    public long recharge;

    public float damage;
    public float projectileSpeed;

    @Override
    public void write(Json json) {
        json.writeValue("type", type);
        json.writeValue("rotationSpeed", rotationSpeed);
        json.writeValue("recoil", recoil);
        json.writeValue("recharge", recharge);
        json.writeValue("damage", damage);
        json.writeValue("projectileSpeed", projectileSpeed);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        type = jsonData.getString("type");
        rotationSpeed = jsonData.getFloat("rotationSpeed");
        recharge = jsonData.getLong("recharge");
        recoil = jsonData.getFloat("recoil");
        damage = jsonData.getFloat("damage");
        projectileSpeed = jsonData.getFloat("projectileSpeed");
    }

    @Override
    public HeadData clone() {
        try {
            HeadData clone = (HeadData) super.clone();
            clone.type = type;
            clone.rotationSpeed = rotationSpeed;
            clone.projectileSpeed = projectileSpeed;
            clone.damage = damage;
            clone.recoil = recoil;
            clone.recharge = recharge;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
