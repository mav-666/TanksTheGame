package com.game.code.systems.listeners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.DeadComponent;
import com.game.code.components.SummonsAfterDeathComponent;
import com.game.code.components.SummonsNowComponent;
import com.game.code.utils.Mappers;

public class SummoningAfterDeathListener implements EntityListener {
    public static final Family FAMILY = Family.all(DeadComponent.class, SummonsAfterDeathComponent.class).get();

    private final Mappers mappers = Mappers.getInstance();
    private final Engine engine;

    public SummoningAfterDeathListener(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void entityAdded(Entity entity) {
        SummonsAfterDeathComponent afterDeath = mappers.get(SummonsAfterDeathComponent.class, entity);
        SummonsNowComponent summons = engine.createComponent(SummonsNowComponent.class);

        summons.summonerType = afterDeath.summonerType;
        summons.entityName = afterDeath.entityName;
        summons.offset.set(afterDeath.offset);

        entity.add(summons);
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
