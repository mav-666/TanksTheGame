package com.game.code.EntityBuilding.battlefiled;

import java.lang.reflect.InvocationTargetException;

public abstract class BoundedPlacer implements Placer {

    protected Bounds bounds;

    BoundedPlacer(Bounds bounds) {
        this.bounds = bounds;
    }

    @Override
    public void reset(Bounds bounds) {
        this.bounds = bounds;
    }

    public static BoundedPlacer addFilter(BoundedPlacer boundedPlacer, Class<? extends PlacingFilter> filterType) {
        try {
            return filterType.getConstructor(boundedPlacer.getClass()).newInstance(boundedPlacer);
        } catch (InstantiationException
                | NoSuchMethodException
                | InvocationTargetException
                | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Bounds getBounds() {
        return bounds;
    }
}
