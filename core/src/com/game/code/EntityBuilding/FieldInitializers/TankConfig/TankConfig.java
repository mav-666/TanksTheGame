package com.game.code.EntityBuilding.FieldInitializers.TankConfig;

import com.game.code.EntityBuilding.FieldInitializers.Config.Config;

public record TankConfig(
        CabConfig cabConfig,
        HeadConfig headConfig,
        BarrelConfig barrelConfig
) implements Config {
    public TankConfig() {
        this(new CabConfig(), new HeadConfig(), new BarrelConfig());
    }
}
