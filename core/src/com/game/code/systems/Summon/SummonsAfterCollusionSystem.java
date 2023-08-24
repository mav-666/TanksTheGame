package com.game.code.systems.Summon;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.*;
import com.game.code.utils.Mappers;

public class SummonsAfterCollusionSystem extends IteratingSystem {

    public SummonsAfterCollusionSystem() {
        super(Family.all(StartCollusionComponent.class, StartsCollusionComponent.class,
                SummonsAfterCollusionComponent.class).get());


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Mappers.get(StartCollusionComponent.class, entity).involved.forEach(involved -> {
            ComponentMapper<TransformComponent> transformM = Mappers.getMapper(TransformComponent.class);

            SummonsAfterCollusionComponent afterCollusion = Mappers.get(SummonsAfterCollusionComponent.class, entity);
            SummonsNowComponent summons = getEngine().createComponent(SummonsNowComponent.class);

            summons.summonerType = afterCollusion.summonerType;
            summons.entityName = afterCollusion.entityName;
            summons.offset.set(transformM.get(entity).position).sub(transformM.get(involved).position).scl(-1, -1);

            entity.add(summons);
        });

    }
}
