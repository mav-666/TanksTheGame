package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.game.code.utils.Event;

public class ButtonTemplateComponent implements Component, Pool.Poolable {
    public Event activateEvent;

    @Override
    public void reset() {
        activateEvent = null;
    }
}
