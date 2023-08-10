package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;
import com.game.code.utils.Bounds;

public class IntPlacer extends LimitedPlacer {

    private final Placer placer;

    public IntPlacer(Bounds bounds, Placer placer) {
        super(bounds);

        this.placer = placer;
    }

    @Override
    public Vector2 nextSpot() {
        Vector2 spot = placer.nextSpot();
        return spot.set((int) spot.x, (int) spot.y);
    }

    @Override
    public boolean hasNext() {
        return placer.hasNext();
    }
}
