package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;
import com.game.code.EntityBuilding.SummonerType;

public interface EntityCreator {

    void createEntityOn(Vector2 spot, float zIndex, String entityName);

    default void createEntityOn(Vector2 spot, String entityName) {
        createEntityOn(spot, 0, entityName);
    }

    void setSummonerType(SummonerType summonerType);
}
