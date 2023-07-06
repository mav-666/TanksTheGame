package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.DestroyedComponent;
import com.game.code.components.SummonsAfterRemoveComponent;
import com.game.code.components.SummonsComponent;
import com.game.code.utils.Mappers;

public class SummonsAfterRemoveSystem extends IteratingSystem {

    private final Mappers mappers;

    public SummonsAfterRemoveSystem() {
        super(Family.all(SummonsAfterRemoveComponent.class, DestroyedComponent.class).get());

        mappers = Mappers.getInstance();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SummonsAfterRemoveComponent summonsAfterRemove = mappers.get(SummonsAfterRemoveComponent.class).get(entity);
        SummonsComponent summonsParticle = getEngine().createComponent(SummonsComponent.class);

        summonsParticle.summonerType = summonsAfterRemove.summonerType;
        summonsParticle.entityName = summonsAfterRemove.entityName;

        entity.add(summonsParticle);
        entity.remove(SummonsAfterRemoveComponent.class);
    }
}
