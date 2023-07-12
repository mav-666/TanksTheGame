package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectSet;
import com.game.code.utils.Bounds;

import java.util.Random;

public class UniqueRandomPlacer extends RandomPlacer {

    ObjectSet<Vector2> pastSpots = new ObjectSet<>();

    public UniqueRandomPlacer(Bounds bounds, Random random) {
        super(bounds, random);
    }

    @Override
    public Vector2 nextSpot() {
        Vector2 nextSpot = super.nextSpot();
        if(pastSpots.contains(nextSpot))
            return nextSpot();
        pastSpots.add(nextSpot);
        return nextSpot;
    }
}
