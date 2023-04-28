package com.game.code.BattleField;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class PlainBuilderData extends BattleFieldBuilderJsonData {
    public String plainTileName;

    @Override
    public void write(Json json) {
        super.write(json);

        json.writeValue("plainTileName", plainTileName);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);

        plainTileName = jsonData.getString("plainTileName");
    }
}
