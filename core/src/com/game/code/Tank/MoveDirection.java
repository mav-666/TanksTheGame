package com.game.code.Tank;

public enum MoveDirection {
    forward(1),
    backward(-1);

    public int mult;

    MoveDirection(int direction){
        mult= direction;
    }
}
