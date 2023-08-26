package com.game.code.systems.Box2d.Contact;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.game.code.components.ContactEventComponent;
import com.game.code.components.EndsCollusionComponent;
import com.game.code.utils.Mappers;

public class ContactEventSystem implements EntityListener {
    public static final Family FAMILY = Family.all(EndsCollusionComponent.class, ContactEventComponent.class).get();

    @Override
    public void entityAdded(Entity entity) {
        Mappers.get(ContactEventComponent.class, entity).event.occur();
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
