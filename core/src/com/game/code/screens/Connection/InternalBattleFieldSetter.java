package com.game.code.screens.Connection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonWriter;
import com.game.code.BattleField.*;

public class InternalBattleFieldSetter implements BattleFieldSetter {
    Json json;
    BattleFieldBuilderJsonData battleFieldBuilderJsonData;

    InternalBattleFieldSetter() {
        json = new Json();
        JsonReader jsonReader = new JsonReader();

        battleFieldBuilderJsonData = json.fromJson(BattleFieldBuilderJsonData.class, jsonReader.parse(Gdx.files.internal("Json/BattleField.json")).toJson(JsonWriter.OutputType.json));
    }

    @Override
    public void setType(BattleFieldBuilders type) {
        battleFieldBuilderJsonData.type = type.name();
    }

    @Override
    public void setSeed(int seed) {
        battleFieldBuilderJsonData.seed = seed;
    }

    @Override
    public void setSize(int width, int height) {
        battleFieldBuilderJsonData.width = width;
        battleFieldBuilderJsonData.height = height;
    }

    @Override
    public void addContent(BattleFieldContentSetter contentSetter) {
        battleFieldBuilderJsonData.decorators.add(contentSetter.getData());
    }

    @Override
    public BattleFieldBuilderJsonData getSettings() {
        return battleFieldBuilderJsonData;
    }
}
