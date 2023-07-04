package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;

public abstract class PlacingFilter extends BoundedPlacer {

    protected BoundedPlacer boundedPlacer;

    protected Vector2 spot;

    PlacingFilter(Bounds bounds, BoundedPlacer boundedPlacer) {
        super(bounds);
        this.boundedPlacer = boundedPlacer;
    }

    @Override
    public Vector2 nextSpotFor(EntityTemplate entitySpawn) {
        return spot;
    }

    @Override
    public boolean hasNextFor(EntityTemplate entitySpawn) {
        spot = boundedPlacer.nextSpotFor(entitySpawn);

        return checkSpotFor(entitySpawn);
    }

    @Override
    public void reset(Bounds bounds) {
        super.reset(bounds);
        boundedPlacer.reset(bounds);
    }

    protected abstract boolean checkSpotFor(EntityTemplate entitySpawn);


}
