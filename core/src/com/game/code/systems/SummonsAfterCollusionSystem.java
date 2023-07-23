package com.game.code.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.*;
import com.game.code.utils.Mappers;

public class SummonsAfterCollusionSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();

    public SummonsAfterCollusionSystem() {
        super(Family.all(StartCollusionComponent.class, StartsCollusionComponent.class,
                SummonsAfterCollusionComponent.class).get());


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        mappers.get(StartCollusionComponent.class, entity).involved.forEach(involved -> {
            ComponentMapper<TransformComponent> transformM = mappers.getMapper(TransformComponent.class);

            SummonsAfterCollusionComponent afterCollusion = mappers.get(SummonsAfterCollusionComponent.class, entity);
            SummonsNowComponent summons = getEngine().createComponent(SummonsNowComponent.class);

            summons.summonerType = afterCollusion.summonerType;
            summons.entityName = afterCollusion.entityName;
            summons.offset.set(transformM.get(entity).position).sub(transformM.get(involved).position).scl(-1, -1);

            entity.add(summons);
        });

    }
}
