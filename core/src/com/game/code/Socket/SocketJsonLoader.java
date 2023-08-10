package com.game.code.Socket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.game.code.EntityBuilding.Json.CachedLoader;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketJsonLoader extends CachedLoader {

    private final JsonReader jsonReader = new JsonReader();
    private final JSONObject source;

    SocketJsonLoader(JSONObject source) {
        this.source = source;
    }

    @Override
    protected JsonValue loadFromSource(String name) {
        try {
            return jsonReader.parse(source.getJSONObject(name).toString());
        } catch (JSONException e) {
            Gdx.app.log("JsonLoadingError", "source does not have such json: " + name);
            return new JsonValue("");
        }
    }
}
