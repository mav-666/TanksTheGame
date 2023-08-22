package com.game.code.systems.Sound.listeners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.audio.Sound;
import com.game.code.components.*;
import com.game.code.utils.Mappers;

public class AimingSoundListener implements EntityListener {
    public static final Family FAMILY = Family.all(AimsComponent.class, AimingSoundComponent.class, SoundComponent.class).get();

    private final Mappers mappers = Mappers.getInstance();
    private final Engine engine;

    public AimingSoundListener(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void entityAdded(Entity entity) {
        Sound sound = mappers.get(AimingSoundComponent.class, entity).sound;

        mappers.get(SoundComponent.class, entity).sounds.add(sound);

        entity.add(engine.createComponent(SoundsComponent.class));
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
