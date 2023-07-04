package com.game.code.systems;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.game.code.components.*;
import com.game.code.components.TakesDamageComponent;
import com.game.code.utils.Mappers;
import com.game.code.utils.TweenUtils.ColorAccessor;
import com.game.code.utils.TweenUtils.TweenM;

public class DamagingSystem extends IteratingSystem {

    private final Mappers mappers;
    private final Color damaged;

    public DamagingSystem() {
        super(Family.all(HealthComponent.class, TakesDamageComponent.class).get(), 90);

        mappers = Mappers.getInstance();
        damaged = Color.valueOf("781f2c");
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        if(isInvincible(entity) || mappers.get(HealthComponent.class).get(entity).health <= 0) {
            entity.remove(TakesDamageComponent.class);
            return;
        }

        dealDamageTo(entity);

        makeInvincible(entity);
    }

    private boolean isInvincible(Entity entity) {
        return mappers.get(InvincibleComponent.class).has(entity);
    }

    private void dealDamageTo(Entity entity) {
        HealthComponent health = mappers.get(HealthComponent.class).get(entity);
        TakesDamageComponent damage = mappers.get(TakesDamageComponent.class).get(entity);

        health.health = Math.max(0, health.health - damage.damage);
        entity.remove(TakesDamageComponent.class);
    }

    private void makeInvincible(Entity entity) {
        ComponentMapper<InvincibilityComponent> invincibilityM = mappers.get(InvincibilityComponent.class);

        if(!invincibilityM.has(entity))
            return;

        entity.add(getEngine().createComponent(InvincibleComponent.class));

        Timeline.createSequence()
                .push(Tween.call((type, source) -> addBlinkEffectOn(entity, invincibilityM.get(entity).seconds)))
                .pushPause(invincibilityM.get(entity).seconds)
                .push(Tween.call((type, source) -> entity.remove(InvincibleComponent.class)))
                .start(TweenM.getInstance().getManager());


    }

    private void addBlinkEffectOn(Entity entity, float seconds) {
        ComponentMapper<TextureComponent> textureM = mappers.get(TextureComponent.class);

        if(!textureM.has(entity))
            return;

        Color color = textureM.get(entity).color;
        Color startColor = new Color(color);

        Timeline.createSequence()
                .push(Tween.to(color, ColorAccessor.RGB, 0.05f).target(damaged.r, damaged.g, damaged.b))
                .push(Tween.to(color, ColorAccessor.RGB, 0.05f).target(startColor.r,startColor.g,startColor.b))
                .repeat((int)(seconds/0.11f), 0)
                .start(TweenM.getInstance().getManager());
    }
 }
