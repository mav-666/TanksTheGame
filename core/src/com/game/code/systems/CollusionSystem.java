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
    private final ComponentMapper<StartCollusionComponent> startCollusionM = mappers.getMapper(StartCollusionComponent.class);
    private final ComponentMapper<EndCollusionComponent> endCollusionM = mappers.getMapper(EndCollusionComponent.class);

    public CollusionSystem() {
        super(Family.one(StartCollusionComponent.class, EndCollusionComponent.class).get(), 98);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        removeStartsCollusion(entity);
        removeEndsCollusion(entity);

    }

    private void removeStartsCollusion(Entity entity) {
        if(startCollusionM.has(entity)) {
            startCollusionM.get(entity).involved.clear();
            entity.remove(StartsCollusionComponent.class);
        }
    }

    private void removeEndsCollusion(Entity entity) {
        if(endCollusionM.has(entity)) {
            endCollusionM.get(entity).involved.clear();
            entity.remove(EndsCollusionComponent.class);
        }
    }
}
