package com.game.code.BattleField;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.game.code.Tank.TankData;

public class TankBuilderData extends BattleFieldBuilderDecoratorJsonData {
    public Array<TankData> tanksData;

    @Override
    public void write(Json json) {
        super.write(json);

        json.writeValue("tanks", tanksData);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);

        for (JsonValue tank : jsonData.get("tanks")) {
            tanksData.add(json.fromJson(tank.getClass().asSubclass(TankData.class), tank.toJson(JsonWriter.OutputType.json)));
        }

    }
}
