package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class RandomPlacer extends BoundedPlacer {

    Bounds bounds;
    Random random;

    RandomPlacer(Bounds bounds, Random random) {
        super(bounds);
        this.random = random;
    }

    @Override
    public Vector2 nextSpotFor(EntityTemplate entitySpawn) {
        return new Vector2(random.nextFloat(bounds.startX(), bounds.width()), random.nextFloat(bounds.startY(), bounds.height()));
    }

    @Override
    public boolean hasNextFor(EntityTemplate entitySpawn) {
        return true;
    }
}
