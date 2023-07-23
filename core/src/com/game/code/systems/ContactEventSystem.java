package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.ContactEventComponent;
import com.game.code.components.EndsCollusionComponent;
import com.game.code.utils.Mappers;

public class ContactEventSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();

    public ContactEventSystem() {
        super(Family.all(EndsCollusionComponent.class, ContactEventComponent.class).get(), 5);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        mappers.get(ContactEventComponent.class, entity).event.occur();
    }
}
