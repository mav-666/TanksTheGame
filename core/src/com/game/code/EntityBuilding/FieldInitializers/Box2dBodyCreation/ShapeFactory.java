package com.game.code.EntityBuilding.FieldInitializers.Box2dBodyCreation;

import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.FloatArray;

public interface ShapeFactory {

    Shape createShape(FloatArray coordinates);

}
