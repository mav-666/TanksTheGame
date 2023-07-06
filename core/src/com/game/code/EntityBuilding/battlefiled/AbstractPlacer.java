package com.game.code.EntityBuilding.battlefiled;

public abstract class AbstractPlacer implements Placer {

    protected Bounds bounds;

    AbstractPlacer(Bounds bounds) {
        this.bounds = bounds;
    }

    @Override
    public void reset(Bounds bounds) {
        this.bounds = bounds;
    }

    @Override
    public Bounds getBounds() {
        return bounds;
    }
}
