package com.game.code.systems.listeners;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Color;
import com.game.code.components.*;
import com.game.code.utils.Mappers;
import com.game.code.utils.TweenUtils.ColorAccessor;
import com.game.code.utils.TweenUtils.TweenM;

public class InvincibilityListener implements EntityListener {
    public static final Family FAMILY = Family.all(InvincibilityComponent.class, InvincibleComponent.class).get();

    private final Mappers mappers = Mappers.getInstance();
    private final Color damaged = Color.valueOf("781f2c");

    Color startColor = new Color();

    public InvincibilityListener() {

    }

    @Override
    public void entityAdded(Entity entity) {
        float invincibilitySeconds = mappers.get(InvincibilityComponent.class, entity).seconds;

        Timeline.createSequence()
                .push(Tween.call((type, source) -> addBlinkEffectOn(entity, invincibilitySeconds)))
                .pushPause(invincibilitySeconds)
                .push(Tween.call((type, source) -> entity.remove(InvincibleComponent.class)))
                .start(TweenM.getManager());
    }

    private void addBlinkEffectOn(Entity entity, float seconds) {
        Color color = mappers.getOrCreate(ColorComponent.class, entity).color;
        startColor.set(color);

        Timeline.createSequence()
                .push(Tween.to(color, ColorAccessor.RGB, 0.05f).target(damaged.r, damaged.g, damaged.b))
                .push(Tween.to(color, ColorAccessor.RGB, 0.05f).target(startColor.r,startColor.g,startColor.b))
                .repeat((int)(seconds/0.11f), 0)
                .start(TweenM.getManager());
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
