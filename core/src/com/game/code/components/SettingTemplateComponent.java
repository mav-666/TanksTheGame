package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.ObjectFloatMap;
import com.badlogic.gdx.utils.Pool;
import com.game.code.UI.Meter;

public class SettingTemplateComponent implements Component, Pool.Poolable {
    public boolean isDisabled = false;
    public ObjectFloatMap<String> setting;
    public String labelText = "[%50]{value}%";
    public Meter.MeterConfig meterConfig;

    @Override
    public void reset() {
        isDisabled = false;
        setting = null;
        labelText = "[%50]{value}%";
        meterConfig = null;
    }
}
