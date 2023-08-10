package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.game.code.EntityBuilding.battlefiled.BattlefieldSettings;

public class BattlefieldSettingTemplateComponent implements Component, Pool.Poolable {
    public BattlefieldSettings battlefieldSettings;

    @Override
    public void reset() {
        battlefieldSettings = null;
    }
}
