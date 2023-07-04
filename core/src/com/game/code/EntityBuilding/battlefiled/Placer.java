package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;

public interface Placer {

    void reset(Bounds bounds);

    Vector2 nextSpotFor(EntityTemplate entitySpawn);

    boolean hasNextFor(EntityTemplate entitySpawn);

    Bounds getBounds();
}
