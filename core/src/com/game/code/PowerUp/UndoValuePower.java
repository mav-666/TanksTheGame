package com.game.code.PowerUp;

import com.game.code.Tank.Tank;

@FunctionalInterface
public interface UndoValuePower {

    void undo(Tank tank, float value);
}
