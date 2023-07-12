package com.game.code.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.game.code.components.*;
import com.game.code.utils.Mappers;

public class ContactMaskSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();

    public ContactMaskSystem() {
        super(Family.all(ContactMaskComponent.class)
                .one(CollusionComponent.class, EndCollusionComponent.class)
                .one(CollidesComponent.class, EndsCollusionComponent.class).get());


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ComponentMapper<CollusionComponent> collusionM = mappers.get(CollusionComponent.class);
        ComponentMapper<EndCollusionComponent> endCollusionM = mappers.get(EndCollusionComponent.class);
        if(collusionM.has(entity))
            collusionM.get(entity).involved.forEach(this::mask);

        if(endCollusionM.has(entity))
            endCollusionM.get(entity).involved.forEach(this::expose);
    }

    private void mask(Entity involved) {
        Color involvedColor = mappers.get(TextureComponent.class).get(involved).color;

        float transperancy = 0;
        if(mappers.get(PlayerComponent.class).has(involved))
            transperancy = 0.5f;

        involvedColor.a = transperancy;
    }

    private void expose(Entity involved) {
        Color involvedColor = mappers.get(TextureComponent.class).get(involved).color;

        involvedColor.a = 1;
    }
}
