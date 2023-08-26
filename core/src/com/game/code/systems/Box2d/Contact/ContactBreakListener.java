package com.game.code.systems.Box2d.Contact;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.*;
import com.game.code.components.CollidesComponent;

public class ContactBreakListener implements EntityListener {
    public static final Family FAMILY =  Family.all(CollidesComponent.class, ContactBreakComponent.class).get();

    private final Engine engine;

    public ContactBreakListener(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void entityAdded(Entity entity) {
        entity.add(engine.createComponent(DestroyedComponent.class));
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}