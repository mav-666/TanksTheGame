package com.game.code.Entity;

public interface Breakable extends Entity {

    float getHealth();

    //returns true if damage is taken
    boolean takeDamage(float damage);

    void die();

    @Override
    default short getCategory() {
        return BitCategories.BREAKABLE.bit();
    }
}
