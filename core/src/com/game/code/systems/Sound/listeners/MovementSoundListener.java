package com.game.code.systems.Sound.listeners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.audio.Sound;
import com.game.code.components.*;
import com.game.code.utils.Mappers;
import com.game.code.utils.PlayingSound;

public class MovementSoundListener implements EntityListener {
    public static final Family FAMILY = Family.all(MovesComponent.class, MovingSoundComponent.class, SoundComponent.class).get();
    private final Engine engine;

    public MovementSoundListener(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void entityAdded(Entity entity) {
        if(Mappers.get(MovesComponent.class, entity).movementDirection == 0)
            return;

        MovingSoundComponent movingSoundC = Mappers.get(MovingSoundComponent.class, entity);
        SoundComponent soundC = Mappers.get(SoundComponent.class, entity);

        if(movingSoundC.currentSound == null || !soundC.loopingSounds.contains(movingSoundC.currentSound, true)) {
            Sound sound = movingSoundC.sound;
            movingSoundC.currentSound = PlayingSound.create().sound(sound).build();
            movingSoundC.currentSound.isPaused = false;
            soundC.loopingSounds.add(movingSoundC.currentSound);
        }

        soundC.playingSounds.add(movingSoundC.currentSound);

        entity.add(engine.createComponent(SoundsComponent.class));
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
