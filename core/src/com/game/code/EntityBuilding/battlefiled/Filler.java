package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;

import java.util.function.Predicate;

public interface Filler {

    void fill(BattleFieldTemplate battleFieldTemplate);

    void setPlacer(Placer placer);

    void setFilling(EntityTemplate entitySpawn);

    void addFilter(Predicate<Vector2> filter);
}
