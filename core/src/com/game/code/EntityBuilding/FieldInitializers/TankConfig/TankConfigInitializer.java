package com.game.code.EntityBuilding.FieldInitializers.TankConfig;

import com.game.code.EntityBuilding.FieldInitializers.Config.ConfigFactory;
import com.game.code.EntityBuilding.FieldInitializers.FieldInitializer;

public class TankConfigInitializer extends FieldInitializer<TankConfig, String> {

    private final ConfigFactory<TankConfig> tankConfigFactory;

    public TankConfigInitializer(ConfigFactory<TankConfig> tankConfigFactory) {
        this.tankConfigFactory = tankConfigFactory;
    }

    @Override
    public TankConfig getInitBy(String config) {
        return tankConfigFactory.get(config);
    }
}
