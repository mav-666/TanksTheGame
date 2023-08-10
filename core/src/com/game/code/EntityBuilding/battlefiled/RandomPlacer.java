package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;
import com.game.code.utils.Bounds;

import java.util.Random;

public class RandomPlacer extends LimitedPlacer {

    private final Random random;

    public RandomPlacer(Bounds bounds, Random random) {
        super(bounds);
        this.random = random;
    }

    @Override
    public Vector2 nextSpot() {
        return new Vector2(random.nextFloat(bounds.startX(), bounds.width()), random.nextFloat(bounds.startY(), bounds.height()));
    }

    @Override
    public boolean hasNext() {
        return true;
    }
}
