package com.game.code.Entity;

public interface Projectile extends Entity {

    @Override
    default short getCategory() {
        return BitCategories.PROJECTILE.bit();
    }
}
