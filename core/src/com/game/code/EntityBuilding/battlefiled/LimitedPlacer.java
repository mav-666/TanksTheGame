package com.game.code.EntityBuilding.battlefiled;

import com.game.code.utils.Bounds;

public abstract class LimitedPlacer extends Placer {

    protected int currentPlaced = 0;
    protected int maxPlaced = -1;

    LimitedPlacer(Bounds bounds) {
        super(bounds);
    }

    @Override
    public void reset(Bounds bounds) {
        currentPlaced = 0;
    }

    public void setMaxPlaced(int maxPlaced) {
        this.maxPlaced = maxPlaced;
    }

    protected boolean isNotEnough() {
        return maxPlaced == -1 || currentPlaced < maxPlaced;
    }

    public void iterate(Place place) {
        reset();
        do {
            place.on(nextSpot());
            currentPlaced++;
        } while (hasNext() && isNotEnough());
    }
}
