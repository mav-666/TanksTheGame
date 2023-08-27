package com.game.code.utils.TweenUtils;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.physics.box2d.Body;

public class BodyTransformAccessor implements TweenAccessor<Body> {

    public static final int X = 0;
    public static final int Y = 1;
    public static final int XY = 2;
    public static final int ANGLE = 3;

    @Override
    public int getValues(Body target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case X -> returnValues[0] = target.getPosition().x;
            case Y -> returnValues[0] = target.getPosition().y;
            case XY -> {
                returnValues[0] = target.getPosition().x;
                returnValues[1] = target.getPosition().y;
                return 2;
            }
            case ANGLE -> returnValues[0] = target.getAngle();

            default -> {return -1;}
        }
        return 1;
    }

    @Override
    public void setValues(Body target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case X -> target.setTransform(newValues[0], target.getPosition().y, target.getAngle());
            case Y -> target.setTransform(target.getPosition().x, newValues[0], target.getAngle());
            case XY -> target.setTransform(newValues[0], newValues[1], target.getAngle());
            case ANGLE -> target.setTransform(target.getPosition(), newValues[0]);
        }
    }
}
