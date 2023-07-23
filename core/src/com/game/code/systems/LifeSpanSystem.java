package com.game.code.systems;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.DestroyedComponent;
import com.game.code.components.LifeSpanComponent;
import com.game.code.utils.Mappers;
import com.game.code.utils.TweenUtils.TweenM;

public class LifeSpanSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();

    public LifeSpanSystem() {
        super(Family.all(LifeSpanComponent.class).get());


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        float lifeSpan = mappers.get(LifeSpanComponent.class, entity).seconds;

        entity.remove(LifeSpanComponent.class);

        Timeline.createSequence()
                .pushPause(lifeSpan)
                .push(Tween.call((type, source) -> entity.add(getEngine().createComponent(DestroyedComponent.class))))
                .start(TweenM.getInstance().getManager());
    }
}
