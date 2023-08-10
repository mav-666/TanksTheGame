package com.game.code.EntityBuilding.FieldInitializers.TankConfig;

import com.game.code.EntityBuilding.FieldInitializers.Config.Config;

public record HeadConfig(
        String headName,
        float rotationSpeed
) implements Config {
    public HeadConfig() {
        this("Head", 2);
    }
}