package com.game.code.EntityBuilding.FieldInitializers;

public class BooleanInitializer extends FieldInitializer<Boolean, Boolean> {

    @Override
    public Boolean getInitBy(Boolean config) {
        return config;
    }
}
