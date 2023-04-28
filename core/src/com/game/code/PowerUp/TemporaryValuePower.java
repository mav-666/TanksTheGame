package com.game.code.PowerUp;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.game.code.Tank.Tank;

public interface TemporaryValuePower {

    default void activate(ValuePower power, UndoValuePower undoPower, Tank tank, float value, float duration) {
        tank.addAction(Actions.sequence(
                Actions.run(() -> power.activate(tank, value)),
                Actions.delay(duration),
                Actions.run(() -> undoPower.undo(tank, value))
        ));
    };
}
