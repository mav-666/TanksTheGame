package com.game.code.EntityBuilding.FieldInitializers.TankConfig;

import com.game.code.EntityBuilding.FieldInitializers.Config.Config;

public record PlayerConfig(
    String playerName,
    String Id,
    TankConfig tankConfig
) implements Config {

}
