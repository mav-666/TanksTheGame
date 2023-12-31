package com.game.code.systems.Death;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.*;
import com.game.code.utils.Mappers;

public class DamagingSystem extends IteratingSystem {

    public DamagingSystem() {
        super(Family.all(HealthComponent.class, TakesDamageComponent.class).get(), 90);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        HealthComponent healthC = Mappers.get(HealthComponent.class, entity);

        if(!Mappers.has(IdComponent.class, entity) && (isInvincible(entity) || healthC.currentHP <= 0)) {
            entity.remove(TakesDamageComponent.class);
            return;
        }

        dealDamageTo(entity);

        if(healthC.currentHP > 0)
            makeInvincible(entity);
    }

    private boolean isInvincible(Entity entity) {
        return Mappers.has(InvincibleComponent.class, entity);
    }

    private void dealDamageTo(Entity entity) {
        HealthComponent health = Mappers.get(HealthComponent.class, entity);
        TakesDamageComponent damage = Mappers.get(TakesDamageComponent.class, entity);

        health.currentHP = Math.max(0, health.currentHP - damage.damage);
        entity.remove(TakesDamageComponent.class);
    }

    private void makeInvincible(Entity entity) {
        if(!Mappers.has(InvincibilityComponent.class, entity))
            return;

        entity.add(getEngine().createComponent(InvincibleComponent.class));
    }
 }
