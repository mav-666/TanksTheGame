package com.game.code.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.EndCollusionComponent;
import com.game.code.components.EndsCollusionComponent;
import com.game.code.components.StartCollusionComponent;
import com.game.code.components.StartsCollusionComponent;
import com.game.code.utils.Mappers;

public class CollusionSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();

    public CollusionSystem() {
        super(Family.one(StartCollusionComponent.class, EndCollusionComponent.class).get(), 99);


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ComponentMapper<StartCollusionComponent> startCollusionM = mappers.get(StartCollusionComponent.class);
        if(startCollusionM.has(entity)) {
            startCollusionM.get(entity).involved.clear();
            entity.remove(StartsCollusionComponent.class);
        }

        ComponentMapper<EndCollusionComponent> endCollusionM = mappers.get(EndCollusionComponent.class);
        if(endCollusionM.has(entity)) {
            endCollusionM.get(entity).involved.clear();
            entity.remove(EndsCollusionComponent.class);
        }

    }
}
