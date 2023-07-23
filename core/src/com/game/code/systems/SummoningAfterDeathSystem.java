package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.DeadComponent;
import com.game.code.components.SummonsAfterDeathComponent;
import com.game.code.components.SummonsNowComponent;
import com.game.code.utils.Mappers;

public class SummoningAfterDeathSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();

    public SummoningAfterDeathSystem() {
        super(Family.all(DeadComponent.class, SummonsAfterDeathComponent.class).get());


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SummonsAfterDeathComponent afterDeath = mappers.get(SummonsAfterDeathComponent.class, entity);
        SummonsNowComponent summons = getEngine().createComponent(SummonsNowComponent.class);

        summons.summonerType = afterDeath.summonerType;
        summons.entityName = afterDeath.entityName;
        summons.offset.set(afterDeath.offset);

        entity.add(summons);
        entity.remove(SummonsAfterDeathComponent.class);
    }
}
