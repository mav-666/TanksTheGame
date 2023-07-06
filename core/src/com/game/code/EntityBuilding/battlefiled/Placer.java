package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;

public interface Placer {

    void reset(Bounds bounds);

    Vector2 nextSpot();

    boolean hasNext();

    Bounds getBounds();
}
