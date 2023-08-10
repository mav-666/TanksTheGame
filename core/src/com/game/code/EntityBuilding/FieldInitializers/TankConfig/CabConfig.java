package com.game.code.EntityBuilding.FieldInitializers.TankConfig;

import com.game.code.EntityBuilding.FieldInitializers.Config.Config;

public record CabConfig(
        String cabName,
        float movementSpeed,
        float agility,
        float HP
) implements Config {
    public CabConfig() {
        this("Cab", 10f, 4, 200f);
    }
}
