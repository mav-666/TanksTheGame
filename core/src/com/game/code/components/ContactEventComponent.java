package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.game.code.utils.Event;

public class ContactEventComponent implements Component, Pool.Poolable {
    public Event event = () -> {};

    @Override
    public void reset() {
        event = () -> {};
    }
}


