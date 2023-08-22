package com.game.code.systems;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.components.*;
import com.game.code.utils.Mappers;
import com.game.code.utils.TweenUtils.BodyTransformAccessor;
import com.game.code.utils.TweenUtils.ColorAccessor;
import com.game.code.utils.TweenUtils.TweenM;

public class ScrollSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();
    private ScrollComponent scroll;
    private Vector2 position;

    public ScrollSystem() {
        super(Family.all(ScrollComponent.class, ScrollsComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        scroll = mappers.get(ScrollComponent.class, entity);
        position = mappers.get(TransformComponent.class, entity).position;

        int scrollingDirection = mappers.get(ScrollsComponent.class, entity).scrollingDirection;

        if(scrollingDirection == 0)
            return;

        for(int i = 0; i < scroll.visibleItems; i++) {
            hideItem(scroll.allItems.get(nextIndex(i)), i, scrollingDirection);
        }

        changeItemGroupBy(scrollingDirection);

        for(int i = 0; i < scroll.visibleItems; i++) {
            revealItem(scroll.allItems.get(nextIndex(i)), i, scrollingDirection);
        }

        entity.remove(ScrollsComponent.class);
    }

    private void changeItemGroupBy(int direction) {
        if(direction == -1)
            scroll.currentItemGroup--;
        else if(direction == 1)
            scroll.currentItemGroup++;

    }

    private int nextIndex(int i) {
        int index = i + scroll.currentItemGroup * scroll.visibleItems;

        while (index >= scroll.allItems.size)
            index -= scroll.allItems.size;
        while (index < 0) {
            index += scroll.allItems.size;
        }

        return index;
    }

    private void hideItem(Entity entity, int index, int direction) {
        float currentX = calcItemX(index);

        Body body = mappers.get(BodyComponent.class, entity).body;
        body.setTransform(currentX, position.y, body.getAngle());

        Timeline transition = Timeline.createSequence()
                .beginParallel()
                .push(Tween.to(mappers.getOrCreate(ColorComponent.class, entity).color, ColorAccessor.A, 0.5f).target(0))
                .push(Tween.to(body, BodyTransformAccessor.X, 0.5f).target(calcItemX(calcBoundIndex(-direction))))
                .end()
                .push(Tween.call((type, source) ->  {
                    entity.add(getEngine().createComponent(InvisibleComponent.class));
                    entity.add(getEngine().createComponent(HiddenBodyComponent.class));
                }));

        transition.start(TweenM.getManager());
    }

    private float calcItemX(int index) {
        float center = (scroll.visibleItems - 1) / 2f;
        if(center % 2 != 0)
            center += -0.5 + scroll.padding/2;
        return (index - center) * scroll.padding + position.x;
    }

    private int calcBoundIndex(int direction) {
        if(direction == 1)
            return scroll.visibleItems;

        return -1;
    }

    private void revealItem(Entity entity, int index, int direction) {
        float currentX = calcItemX(calcBoundIndex(direction));

        Body body = mappers.get(BodyComponent.class, entity).body;
        body.setTransform(currentX, position.y, body.getAngle());

        Timeline transition = Timeline.createSequence()
                .push(Tween.call((type, source) ->  {
                    entity.remove(InvisibleComponent.class);
                    entity.remove(HiddenBodyComponent.class);
                }))
                .beginParallel()
                .push(Tween.to(mappers.getOrCreate(ColorComponent.class, entity).color, ColorAccessor.A, .5f).target(1))
                .push(Tween.to(body, BodyTransformAccessor.X, .5f).target(calcItemX(index)))
                .end();


        transition.start(TweenM.getManager());
    }
}
