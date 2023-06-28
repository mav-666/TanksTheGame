package com.game.code.EntityBuilding.FieldInitializers;

public class IntegerInitializer extends FieldInitializer<Integer, Integer> {
    @Override
    public Integer getInitBy(Integer config) {
        return config;
    }
}
