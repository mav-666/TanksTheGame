package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.DestroyedComponent;
import com.game.code.components.SummonsAfterRemoveComponent;
import com.game.code.components.SummonsNowComponent;
import com.game.code.utils.Mappers;

public class SummonsAfterRemoveSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();

    public SummonsAfterRemoveSystem() {
        super(Family.all(SummonsAfterRemoveComponent.class, DestroyedComponent.class).get());


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SummonsAfterRemoveComponent summonsAfterRemove = mappers.get(SummonsAfterRemoveComponent.class).get(entity);
        SummonsNowComponent summons = getEngine().createComponent(SummonsNowComponent.class);

        summons.summonerType = summonsAfterRemove.summonerType;
        summons.entityName = summonsAfterRemove.entityName;
        summons.offset.set(summonsAfterRemove.offset);

        entity.add(summons);
        entity.remove(SummonsAfterRemoveComponent.class);
    }
}
