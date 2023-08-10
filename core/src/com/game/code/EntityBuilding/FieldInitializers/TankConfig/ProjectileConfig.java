package com.game.code.EntityBuilding.FieldInitializers.TankConfig;

import com.game.code.EntityBuilding.FieldInitializers.Config.Config;

public record ProjectileConfig(
        String projectileName,
        float radius,
        float recoil,
        float speed,
        float contactDamage
) implements Config {
    public ProjectileConfig() {
        this("Bullet", 0.1f, 0.2f, 5f, 20f);
    }
}
