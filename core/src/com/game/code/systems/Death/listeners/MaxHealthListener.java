package com.game.code.systems.Death.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.game.code.components.HealthComponent;
import com.game.code.utils.Mappers;

public class MaxHealthListener implements EntityListener {
    public static final Family FAMILY = Family.all(HealthComponent.class).get();

    @Override
    public void entityAdded(Entity entity) {
        HealthComponent healthC = Mappers.get(HealthComponent.class, entity);

        if(healthC.maxHP == -1) {
            healthC.maxHP = healthC.currentHP;
        }
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
