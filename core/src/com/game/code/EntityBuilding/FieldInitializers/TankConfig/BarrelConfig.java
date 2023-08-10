package com.game.code.EntityBuilding.FieldInitializers.TankConfig;

import com.game.code.EntityBuilding.FieldInitializers.Config.Config;

public record BarrelConfig(
        String barrelName,
        float rechargeSeconds,
        ProjectileConfig projectileConfig
) implements Config {
    public BarrelConfig() {
        this("Barrel", 0.75f, new ProjectileConfig());
    }
}
