package com.game.code.systems.Sound.listeners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.game.code.components.*;

public class SoundSearcher implements EntityListener {
    public static final Family FAMILY = Family.one(MovingSoundComponent.class, AimingSoundComponent.class,
            ContactSoundComponent.class, DamagedSoundComponent.class, DeathSoundComponent.class, ShootingSoundComponent.class).get();

    private final Engine engine;

    public SoundSearcher(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void entityAdded(Entity entity) {
        entity.add(engine.createComponent(SoundComponent.class));
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
