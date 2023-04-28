package com.game.code.BattleField;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class ObstacleBuilderData extends BattleFieldBuilderDecoratorJsonData {
    public float density;

    @Override
    public void write(Json json) {
        super.write(json);
        json.writeValue("density", density);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        density = jsonData.getFloat("density");
    }
}
