package com.game.code.systems.Death.listeners;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.game.code.components.*;
import com.game.code.utils.Mappers;
import com.game.code.utils.TweenUtils.TweenM;

public class RespawnListener implements EntityListener {
    public static final Family FAMILY = Family.all(RespawnsAfterDeathComponent.class, DeadComponent.class).get();
    private final Engine engine;

    private final Family spawnFamily = Family.all(SpawnComponent.class, TransformComponent.class).get();

    public RespawnListener(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void entityAdded(Entity entity) {
        float respawnDelay = Mappers.get(RespawnsAfterDeathComponent.class, entity).delaySec;

        Timeline.createSequence()
                .pushPause(respawnDelay)
                .push(Tween.call((type, source) -> respawn(entity)))
                .start(TweenM.getManager());
    }

    private void respawn(Entity entity) {
        resetHealthOf(entity);

        makeInvincible(entity);
        
        placeOnSpawn(entity);
    }
    
    private void resetHealthOf(Entity entity) {
        HealthComponent healthC = Mappers.get(HealthComponent.class, entity);

        healthC.currentHP = healthC.maxHP;
        
        entity.remove(DeadComponent.class);
    }

    private void makeInvincible(Entity entity) {
        entity.add(engine.createComponent(InvincibleComponent.class));
        if(!Mappers.has(InvincibilityComponent.class, entity)) {
            InvincibilityComponent invincibilityC = entity.getComponent(InvincibilityComponent.class);
            invincibilityC.seconds = .5f;
            entity.add(invincibilityC);
        }
    }
    
    private void placeOnSpawn(Entity entity) {
        Entity spawn = engine.getEntitiesFor(spawnFamily).random();
        Vector2 spawnPos = Mappers.get(TransformComponent.class, spawn).position;

        if(Mappers.has(BodyComponent.class, entity))
            Mappers.get(BodyComponent.class, entity).body.setTransform(spawnPos, 0);
        else
            Mappers.get(TransformComponent.class, entity).position.set(spawnPos);
    }

    @Override
    public void entityRemoved(Entity entity) {

    }

    



}
