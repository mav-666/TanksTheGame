package com.game.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

//frequently used combinations of actions
public class Animations {

    //milliseconds
    public static RepeatAction damaged(long invincibilityTime) {
        return Actions.repeat( (int) invincibilityTime/100, Actions.sequence(Actions.color(Color.valueOf("781f2c"), 0.05f), Actions.color(Color.WHITE, 0.05f)));
    }

    //amount of recoil applied
    public static SequenceAction recoil(float amount) {
        return Actions.sequence(Actions.moveBy(0, -amount, 0.1f, Interpolation.fastSlow), Actions.moveBy(0, amount, 0.2f, Interpolation.slowFast));
    }

}
