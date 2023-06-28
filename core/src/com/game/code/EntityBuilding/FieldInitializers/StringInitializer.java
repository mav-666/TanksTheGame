package com.game.code.EntityBuilding.FieldInitializers;

public class StringInitializer extends FieldInitializer<String, String> {

    @Override
    public String getInitBy(String config) {
        return config;
    }
}
