package com.game.code.systems.Attachment.Inherit;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.ColorComponent;
import com.game.code.components.InheritColorComponent;
import com.game.code.utils.Mappers;

public class InheritColorSystem extends IteratingSystem {

    public InheritColorSystem() {
        super(Family.all(ColorComponent.class, InheritColorComponent.class).get(), 13);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ComponentMapper<ColorComponent> colorM = Mappers.getMapper(ColorComponent.class);

        Entity original = Mappers.get(InheritColorComponent.class, entity).target;

        if(original != null && colorM.has(original))
            colorM.get(entity).color.set(colorM.get(original).color);
    }
}
