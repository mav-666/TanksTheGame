package com.game.code.Entity;

public interface Wall extends Entity {

    @Override
    default short getCategory() {
        return BitCategories.WALL.bit();
    }

}
