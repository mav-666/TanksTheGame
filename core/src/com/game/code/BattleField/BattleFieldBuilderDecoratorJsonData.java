package com.game.code.BattleField;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class BattleFieldBuilderDecoratorJsonData implements Json.Serializable {
    public String type;

    @Override
    public void write(Json json) {
        json.writeValue("type", type);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        type = jsonData.getString("type");
    }
}
