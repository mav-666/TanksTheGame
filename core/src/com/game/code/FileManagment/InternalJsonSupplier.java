package com.game.code.FileManagment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.game.code.EntityBuilding.Json.JsonSupplier;

import java.util.Optional;

public class InternalJsonSupplier implements JsonSupplier {

    private final JsonReader jsonReader;

    public InternalJsonSupplier() {
        jsonReader = new JsonReader();
    }

    public Optional<JsonValue> findJson(String name) {
        try {
            return Optional.of(jsonReader.parse(Gdx.files.internal("Json/" + name + ".json")));
        } catch (Exception e) {
            Gdx.app.log("Error", "json reading failed due to non existing json : " + name);
            return Optional.empty();
        }
    }
}
