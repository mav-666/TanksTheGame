package com.game.code.EntityBuilding.Json;

import com.badlogic.gdx.utils.JsonValue;
import com.game.code.EntityBuilding.Config;
import com.game.code.EntityBuilding.ConfigFactory;

public abstract class JsonConfigFactory<T extends Config> implements ConfigFactory<T> {

    private final JsonSupplier jsonSupplier;

    public JsonConfigFactory(JsonSupplier jsonSupplier) {
        this.jsonSupplier = jsonSupplier;
    }

    @Override
    public T get(String configName) {
        var json =  jsonSupplier.findJson(configName);

        if(json.isEmpty())
            return processJson(new JsonValue(0));

        return processJson(json.get());
    }

    protected abstract T processJson(JsonValue jsonValue);


}
