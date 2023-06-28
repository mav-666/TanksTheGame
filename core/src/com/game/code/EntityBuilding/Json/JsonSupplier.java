package com.game.code.EntityBuilding.Json;

import com.badlogic.gdx.utils.JsonValue;

import java.util.Optional;

public interface JsonSupplier {

    Optional<JsonValue> findJson(String name);
}
