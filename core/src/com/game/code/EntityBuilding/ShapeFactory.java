package com.game.code.EntityBuilding;

import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.FloatArray;

public interface ShapeFactory {

    Shape createShape(FloatArray coordinates);

}
