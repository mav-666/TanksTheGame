package com.game.code.PowerUp;

import com.game.code.Tank.Tank;

@FunctionalInterface
public interface ValuePower {

    void activate(Tank tank, float modifier);
}
