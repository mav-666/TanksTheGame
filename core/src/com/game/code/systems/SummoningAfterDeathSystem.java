package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.DeadComponent;
import com.game.code.components.SummonsAfterDeathComponent;
import com.game.code.components.SummonsComponent;
import com.game.code.utils.Mappers;

public class SummoningAfterDeathSystem extends IteratingSystem {

    private final Mappers mappers;

    public SummoningAfterDeathSystem() {
        super(Family.all(DeadComponent.class, SummonsAfterDeathComponent.class).get());

        mappers = Mappers.getInstance();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SummonsComponent summons = getEngine().createComponent(SummonsComponent.class);

        summons.entityName = mappers.get(SummonsAfterDeathComponent.class).get(entity).entityName;

        entity.add(summons);
        entity.remove(SummonsAfterDeathComponent.class);
    }
}
