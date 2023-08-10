package com.game.code.systems.listeners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.*;
import com.game.code.utils.Mappers;


public class SummonsAfterRemoveListener implements EntityListener {
    public static final Family FAMILY = Family.all(DestroyedComponent.class, SummonsAfterRemoveComponent.class).get();

    private final Mappers mappers = Mappers.getInstance();
    private final Engine engine;

    public SummonsAfterRemoveListener(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void entityAdded(Entity entity) {
        SummonsAfterRemoveComponent summonsAfterRemove = mappers.get(SummonsAfterRemoveComponent.class, entity);
        SummonsNowComponent summons = engine.createComponent(SummonsNowComponent.class);

        summons.summonerType = summonsAfterRemove.summonerType;
        summons.entityName = summonsAfterRemove.entityName;
        summons.offset.set(summonsAfterRemove.offset);

        entity.add(summons);
        entity.remove(SummonsAfterRemoveComponent.class);
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
