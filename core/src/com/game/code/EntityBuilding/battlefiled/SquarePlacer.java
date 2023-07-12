package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;
import com.game.code.utils.Bounds;

public class SquarePlacer extends Placer {

    private final Vector2 currentSpot = new Vector2(bounds.startX(),bounds.startY());
    
    public SquarePlacer(Bounds bounds) {
        super(bounds);
    }

    @Override
    public Vector2 nextSpot() {
        return currentSpot.cpy();
    }

    @Override
    public boolean hasNext() {
        if(currentSpot.x == bounds.width() && currentSpot.y == bounds.height())
            return false;

        if(currentSpot.x == bounds.width())
            currentSpot.set(0, ++currentSpot.y);
        else
            currentSpot.add(1,0);
        return true;
    }
}
