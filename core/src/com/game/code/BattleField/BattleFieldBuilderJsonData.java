package com.game.code.BattleField;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

public class BattleFieldBuilderJsonData implements Json.Serializable {
    public String type;

    public int seed;
    public float width;
    public float height;
    public float tileWidth;
    public float tileHeight;

    public Array<BattleFieldBuilderDecoratorJsonData> decorators;

    @Override
    public void write(Json json) {
        json.writeValue("type", type);

        json.writeValue("seed", seed);
        json.writeValue("width", width);
        json.writeValue("height", height);
        json.writeValue("tileWidth", tileWidth);
        json.writeValue("tileHeight", tileHeight);

        json.writeValue("decorators", decorators);

    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        type = jsonData.getString("type");

        seed = jsonData.getInt("seed");
        width = jsonData.getFloat("width");
        height = jsonData.getFloat("height");
        tileWidth = jsonData.getFloat("tileWidth");
        tileHeight = jsonData.getFloat("tileHeight");

        decorators = new Array<>();

        for (JsonValue decorator : jsonData.get("decorators")) {
            try {
                decorators.add(json.fromJson(Class.forName(decorator.getString("class")).asSubclass(BattleFieldBuilderDecoratorJsonData.class), decorator.toJson(JsonWriter.OutputType.json)));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
