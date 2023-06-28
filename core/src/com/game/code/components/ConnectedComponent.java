package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

public class ConnectedComponent implements Component, Pool.Poolable {
    public Body target;
    public Vector2 connectionPoint = new Vector2(0, 0);

    @Override
    public void reset() {
        target = null;
        connectionPoint.set(0, 0);
    }
}
