package com.game.code.systems.Box2d.listeners;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.DestroyedComponent;
import com.game.code.components.LifeSpanComponent;
import com.game.code.utils.Mappers;
import com.game.code.utils.TweenUtils.TweenM;

public class LifeSpanListener implements EntityListener {
    public static final Family FAMILY = Family.all(LifeSpanComponent.class).get();
    private final Engine engine;

    public LifeSpanListener(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void entityAdded(Entity entity) {
        float lifeSpan = Mappers.get(LifeSpanComponent.class, entity).seconds;

        Timeline.createSequence()
                .pushPause(lifeSpan)
                .push(Tween.call((type, source) -> entity.add(engine.createComponent(DestroyedComponent.class))))
                .start(TweenM.getManager());
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
