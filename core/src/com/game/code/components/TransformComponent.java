package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

public class TransformComponent implements Component, Pool.Poolable {
    //coordinates 0,0 represent a center point of an entity
    public Vector2 position = new Vector2();

    public float zIndex = 0;

    public float degAngle = 0;
    //by default equals center point
    public Vector2 origin = new Vector2();

    @Override
    public void reset() {
        position.set(0,0);
        zIndex = 0;
        degAngle = 0;
        origin.set(0,0);
    }
}
