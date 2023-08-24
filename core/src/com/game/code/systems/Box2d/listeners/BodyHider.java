package com.game.code.systems.Box2d.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.components.BodyComponent;
import com.game.code.components.HiddenBodyComponent;
import com.game.code.utils.Mappers;

public class BodyHider implements EntityListener {
    public static final Family FAMILY = Family.all(BodyComponent.class, HiddenBodyComponent.class).get();

    @Override
    public void entityAdded(Entity entity) {
        Mappers.get(BodyComponent.class, entity).body.setActive(false);
    }

    @Override
    public void entityRemoved(Entity entity) {
        Mappers.get(BodyComponent.class, entity).body.setActive(true);
    }
}
