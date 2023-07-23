package com.game.code.EntityBuilding.FieldInitializers.Box2dBodyCreation;

import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.FloatArray;

public class ChainFactory implements ShapeFactory {

    @Override
    public Shape createShape(FloatArray coordinates) {
        ChainShape chain = new ChainShape();

        float isLooping = coordinates.removeIndex(0);

        if( isLooping == 1)
            chain.createLoop(coordinates.toArray());
        else
            chain.createChain(coordinates.toArray());


        return chain;
    }
}