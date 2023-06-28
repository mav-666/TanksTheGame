package com.game.code.utils.TweenUtils;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.Color;

public class ColorAccessor implements TweenAccessor<Color> {

    public static final int R = 0;
    public static final int G = 1;
    public static final int B = 2;
    public static final int A = 3;
    public static final int RGB = 4;

    @Override
    public int getValues(Color target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case R -> returnValues[0] = target.r;
            case G -> returnValues[0] = target.g;
            case B -> returnValues[0] = target.b;
            case A -> returnValues[0] = target.a;
            case RGB -> {
                returnValues[0] = target.r;
                returnValues[1] = target.g;
                returnValues[2] = target.b;
                return 3;
            }
            default -> { return -1; }
        }
        return 1;
    }

    @Override
    public void setValues(Color target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case R -> target.r = newValues[0];
            case G -> target.g = newValues[0];
            case B -> target.b = newValues[0];
            case A -> target.a = newValues[0];
            case RGB -> {
                target.r = newValues[0];
                target.g = newValues[1];
                target.b = newValues[2];
            }
        }
    }
}
