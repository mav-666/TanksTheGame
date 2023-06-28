package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.*;
import com.game.code.components.CollidesComponent;

public class ContactBreakSystem extends IteratingSystem {

    public ContactBreakSystem() {
        super(Family.all(CollidesComponent.class, ContactBreakComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        entity.add(getEngine().createComponent(DestroyedComponent.class));
    }

}