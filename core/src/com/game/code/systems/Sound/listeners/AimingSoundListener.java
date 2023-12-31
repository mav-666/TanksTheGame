package com.game.code.systems.Sound.listeners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.audio.Sound;
import com.game.code.components.*;
import com.game.code.utils.Mappers;
import com.game.code.utils.PlayingSound;

public class AimingSoundListener implements EntityListener {
    public static final Family FAMILY = Family.all(AimsComponent.class, AimingSoundComponent.class, SoundComponent.class).get();
    private final Engine engine;

    public AimingSoundListener(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void entityAdded(Entity entity) {
        AimingSoundComponent movingSoundC = Mappers.get(AimingSoundComponent.class, entity);
        SoundComponent soundC = Mappers.get(SoundComponent.class, entity);

        if(movingSoundC.currentSound == null || !soundC.loopingSounds.contains(movingSoundC.currentSound, true)) {
            Sound sound = movingSoundC.sound;
            movingSoundC.currentSound = PlayingSound.create().sound(sound).build();
            soundC.loopingSounds.add(movingSoundC.currentSound);
        }

        soundC.playingSounds.add(movingSoundC.currentSound);

        entity.add(engine.createComponent(SoundsComponent.class));
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
