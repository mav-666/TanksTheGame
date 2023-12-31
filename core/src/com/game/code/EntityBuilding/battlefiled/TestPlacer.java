package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;
import com.game.code.utils.Bounds;

public class TestPlacer extends Placer {

    private boolean tested = false;

    public TestPlacer(Bounds bounds) {
        super(bounds);
    }

    @Override
    public Vector2 nextSpot() {
        tested = true;
        return new Vector2(0,1);
    }

    @Override
    public boolean hasNext() {
        return !tested;
    }
}
