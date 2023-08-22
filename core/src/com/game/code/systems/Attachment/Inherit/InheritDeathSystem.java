package com.game.code.systems.Attachment.Inherit;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.DeadComponent;
import com.game.code.components.InheritDeathComponent;
import com.game.code.utils.Mappers;

public class InheritDeathSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();

    public InheritDeathSystem() {
        super(Family.all(InheritDeathComponent.class).get(), 13);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ComponentMapper<DeadComponent> deadM = mappers.getMapper(DeadComponent.class);

        Entity original = mappers.get(InheritDeathComponent.class, entity).target;

        if(original == null) return;

        if(deadM.has(original))
            entity.add(getEngine().createComponent(DeadComponent.class));
        else
            entity.remove(DeadComponent.class);

    }
}
