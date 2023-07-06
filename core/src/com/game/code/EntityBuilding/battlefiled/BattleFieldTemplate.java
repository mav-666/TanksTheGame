package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectSet;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Iterator;

public interface BattleFieldTemplate {

    void put(Vector2 position, float zIndex, EntityTemplate entitySpawn);

    Iterator<Pair<Float, EntityTemplate>> getEntitiesOn(Vector2 position);

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
