package com.game.code.systems.Sound.listeners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.audio.Sound;
import com.game.code.components.*;
import com.game.code.utils.Mappers;

public class DamagedSoundListener implements EntityListener {
    public static final Family FAMILY = Family.all(TakesDamageComponent.class, DamagedSoundComponent.class, SoundComponent.class).get();
    private final Engine engine;

    public DamagedSoundListener(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void entityAdded(Entity entity) {
        Sound sound = Mappers.get(DamagedSoundComponent.class, entity).sound;

        Mappers.get(SoundComponent.class, entity).sounds.add(sound);

        entity.add(engine.createComponent(SoundsComponent.class));
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
