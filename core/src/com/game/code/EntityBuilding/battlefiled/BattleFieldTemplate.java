package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectSet;

import java.util.Iterator;

public interface BattleFieldTemplate {

    void put(Vector2 position, EntityTemplate entitySpawn);

    Iterator<EntityTemplate> getEntitiesOn(Vector2 position);

    Iterator<Vector2> getAllSpots();

    void clear();

    default boolean isOccupied(Vector2 position) {
        return false;
    }

    default ObjectSet<Vector2> getOccupied() {
        return ObjectSet.with();
    }


    static BattleFieldTemplate adjustRegisterTo(BattleFieldTemplate battleFieldTemplate) {
        return new RegisteringTemplate(battleFieldTemplate);
    }
}
