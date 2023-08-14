package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.ObjectFloatMap;
import com.badlogic.gdx.utils.Pool;
import com.game.code.UI.Meter;

public class MeterTemplateComponent implements Component, Pool.Poolable {
    public ObjectFloatMap<String> setting;
    public String labelText = "[%50]{value}%";
    public Meter.MeterConfig meterConfig;

    @Override
    public void reset() {
        setting = null;
        labelText = "[%50]{value}%";
        meterConfig = null;
    }
}
