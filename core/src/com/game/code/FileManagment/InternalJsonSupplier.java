package com.game.code.FileManagment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.EntityBuilding.Json.JsonSupplier;

import java.util.Optional;

public class InternalJsonSupplier implements JsonSupplier {

    private final JsonReader jsonReader = new JsonReader();
    private final ObjectMap<String, JsonValue> loadedJsons = new ObjectMap<>();


    public Optional<JsonValue> findJson(String name) {
        if(name.equals("")) return Optional.empty();
        if(!loadedJsons.containsKey(name)) {
            var json =  readJson(name);
            if(json.isEmpty())
                return json;

            loadedJsons.put(name, json.get());
        }

        return Optional.of(loadedJsons.get(name));
    }

    private Optional<JsonValue> readJson(String name) {
        try {
            return Optional.of(jsonReader.parse(Gdx.files.internal("Json/" + name + ".json")));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
