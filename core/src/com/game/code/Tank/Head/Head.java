package com.game.code.Tank.Head;

import com.badlogic.gdx.math.Vector2;

public interface Head {

    void aim(float delta, Vector2 aimPosition);


    void shoot();
}
