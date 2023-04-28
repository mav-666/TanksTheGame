package com.game.code.Tank.Cab;

public enum TurnDirection {
    left(1),
    right(-1);

    public int mult;

    TurnDirection(int direction){
        mult= direction;
    }
}