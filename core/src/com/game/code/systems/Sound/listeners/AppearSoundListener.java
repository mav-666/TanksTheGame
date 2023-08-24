package com.game.code.systems.Sound.listeners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.audio.Sound;
import com.game.code.components.AppearSoundComponent;
import com.game.code.components.ContactSoundComponent;
import com.game.code.components.SoundComponent;
import com.game.code.components.SoundsComponent;

import com.game.code.utils.Mappers;

public class AppearSoundListener implements EntityListener {
    public static final Family FAMILY = Family.all(AppearSoundComponent.class, SoundComponent.class).get();
    private final Engine engine;

    public AppearSoundListener(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void entityAdded(Entity entity) {
        Sound sound = Mappers.get(AppearSoundComponent.class, entity).sound;

        Mappers.get(SoundComponent.class, entity).sounds.add(sound);

        entity.add(engine.createComponent(SoundsComponent.class));
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
