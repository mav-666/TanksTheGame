package com.game.code.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.InheritColorComponent;
import com.game.code.components.TextureComponent;
import com.game.code.utils.Mappers;

public class InheritColorSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();

    public InheritColorSystem() {
        super(Family.all(TextureComponent.class, InheritColorComponent.class).get(), 13);


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ComponentMapper<TextureComponent> transformM =  mappers.get(TextureComponent.class);

        Entity original = mappers.get(InheritColorComponent.class).get(entity).target;

        if(original != null)
            transformM.get(entity).color.set(transformM.get(original).color);
    }
}
