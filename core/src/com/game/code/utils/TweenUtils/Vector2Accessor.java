package com.game.code.utils.TweenUtils;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.math.Vector2;

public class Vector2Accessor implements TweenAccessor<Vector2> {

    public static final int X = 0;
    public static final int Y = 1;
    public static final int XY = 2;

    @Override
    public int getValues(Vector2 target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case X -> returnValues[0] = target.x;
            case Y -> returnValues[0] = target.y;
            case XY -> {
                returnValues[0] = target.x;
                returnValues[1] = target.y;
                return 2;
            }
            default -> {return -1;}
        }
        return 1;
    }

    @Override
    public void setValues(Vector2 target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case X -> target.x = newValues[0];
            case Y -> target.y = newValues[0];
            case XY -> {
                target.x = newValues[0];
                target.y = newValues[1];
            }
        }
    }
}
