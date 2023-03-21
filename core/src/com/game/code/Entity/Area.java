package com.game.code.Entity;

public interface Area extends Entity {

    @Override
    default short getCategory() {
        return BitCategories.AREA.bit();
    }

}
