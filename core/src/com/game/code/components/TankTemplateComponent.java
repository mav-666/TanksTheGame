package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.game.code.EntityBuilding.FieldInitializers.TankConfig.TankConfig;

public class TankTemplateComponent implements Component, Pool.Poolable {
    public TankConfig tankConfig;

    @Override
    public void reset() {
        tankConfig = null;
    }
}
