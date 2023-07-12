package com.game.code.EntityBuilding.Json;

import com.badlogic.gdx.utils.JsonValue;
import com.game.code.EntityBuilding.Config;
import com.game.code.EntityBuilding.ConfigFactory;

public abstract class JsonConfigFactory<T extends Config> implements ConfigFactory<T> {

    private final JsonLoader jsonLoader;

    public JsonConfigFactory(JsonLoader jsonLoader) {
        this.jsonLoader = jsonLoader;
    }

    @Override
    public T get(String configName) {
        var json =  jsonLoader.findJson(configName);

        if(json.isEmpty())
            return processJson(new JsonValue(0));

        return processJson(json.get());
    }

    protected abstract T processJson(JsonValue jsonValue);


}
