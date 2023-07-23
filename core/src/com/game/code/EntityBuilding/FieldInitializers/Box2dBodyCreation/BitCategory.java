package com.game.code.EntityBuilding.FieldInitializers.Box2dBodyCreation;

public enum BitCategory {
    Projectile((short) 2),
    ;

    public short bit;

    BitCategory(short bit) {
        this.bit = bit;
    }
}
