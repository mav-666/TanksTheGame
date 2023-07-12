package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;

@FunctionalInterface
public interface Place {

    void on(Vector2 spot);
}
