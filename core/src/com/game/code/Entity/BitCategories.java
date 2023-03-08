package com.game.code.Entity;

public enum BitCategories {
    ALL((short) -1),
    WALL((short) 1),
    BREAKABLE((short) 2),
    PROJECTILE((short) 4),
    ;
    private short bit;

    BitCategories(short bit){
        this.bit= bit;
    }

    public short bit(){
        return bit;
    }
}