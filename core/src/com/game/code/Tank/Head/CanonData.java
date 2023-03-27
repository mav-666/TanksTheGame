package com.game.code.Tank.Head;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class CanonData extends HeadData {
    public float recoil;
    public long recharge;

    public float damage;
    public float projectileSpeed;


    @Override
    public void write(Json json) {
        super.write(json);

        json.writeValue("recoil", recoil);
        json.writeValue("recharge", recharge);
        json.writeValue("damage", damage);
        json.writeValue("projectileSpeed", projectileSpeed);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        recharge = jsonData.getLong("recharge");
        recoil = jsonData.getFloat("recoil");

        damage = jsonData.getFloat("damage");
        projectileSpeed = jsonData.getFloat("projectileSpeed");


    }
}
