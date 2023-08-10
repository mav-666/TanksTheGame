package com.game.code.FileManagment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.game.code.EntityBuilding.Json.CachedLoader;
import org.apache.commons.lang3.StringUtils;

public class InternalJsonLoader extends CachedLoader {

    private final JsonReader jsonReader = new JsonReader();

    @Override
    protected JsonValue loadFromSource(String name) {
        return jsonReader.parse(Gdx.files.internal("Json/" + StringUtils.capitalize(name) + ".json"));
    }

}
