package com.game.code.Tank;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.game.code.Tank.Cab.CabData;
import com.game.code.Tank.Head.HeadData;

public class TankData implements Cloneable, Json.Serializable {
    public String id;
    public HeadData headData;
    public CabData cabData;

    @Override
    public void write(Json json) {
        json.writeValue("id", id);
        json.writeValue("head", headData, HeadData.class);
        json.writeValue("cab", cabData, CabData.class);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        id = jsonData.getString("id");
        headData = json.fromJson(HeadData.class, jsonData.get("head").toJson(JsonWriter.OutputType.json));
        cabData = json.fromJson(CabData.class, jsonData.get("cab").toJson(JsonWriter.OutputType.json));
    }

    @Override
    public TankData clone() {
        try {
            TankData clone = (TankData) super.clone();
            clone.id = id;
            clone.headData = headData.clone();
            clone.cabData = cabData.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
