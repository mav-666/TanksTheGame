package com.game.code.EntityBuilding.battlefiled;

public class EdgeFilter extends PlacingFilter {

    EdgeFilter(Bounds bounds, BoundedPlacer boundedPlacer) {
        super(bounds, boundedPlacer);
    }

    @Override
    protected boolean checkSpotFor(EntityTemplate entitySpawn) {
        return spot.x + entitySpawn.getWidth() <= bounds.width() &&
                spot.y + entitySpawn.getHeight() <= bounds.height();
    }
}
