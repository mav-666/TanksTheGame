package com.game.code.systems.Summon;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;

import com.game.code.components.*;
import com.game.code.utils.Mappers;

public class SummonsWhileMovingSystem extends IntervalIteratingSystem {
    private static final float TIME_STEP = 1/20f;


    private final Mappers mappers = Mappers.getInstance();

    public SummonsWhileMovingSystem() {
        super(Family.all(SummonsWhileMovingComponent.class, MovesComponent.class).get(), TIME_STEP, 11);


    }

    @Override
    protected void processEntity(Entity entity) {
        SummonsWhileMovingComponent whileMoving = mappers.get(SummonsWhileMovingComponent.class, entity);
        SummonsNowComponent summons = getEngine().createComponent(SummonsNowComponent.class);

        summons.summonerType = whileMoving.summonerType;
        summons.entityName = whileMoving.entityName;
        summons.degAngle = mappers.get(TransformComponent.class, entity).degAngle;
        summons.offset.set(whileMoving.offset);

        entity.add(summons);
    }
}
