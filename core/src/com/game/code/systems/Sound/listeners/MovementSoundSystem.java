package com.game.code.systems.Sound.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.game.code.components.MovesComponent;
import com.game.code.components.MovingSoundComponent;
import com.game.code.components.SoundComponent;
import com.game.code.components.SoundsComponent;
import com.game.code.utils.Mappers;
import com.game.code.utils.PlayingSound;

public class MovementSoundSystem extends IteratingSystem {
    private static final float FADE_STEP = 0.25f;

    private final Mappers mappers = Mappers.getInstance();

    private final Array<Entity> allEntities = new Array<>();
    private final Array<Entity> upToDateEntities = new Array<>();

    public MovementSoundSystem() {
        super(Family.all(MovesComponent.class, MovingSoundComponent.class, SoundComponent.class).get(), 11);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        removeNotMoving();
        upToDateEntities.clear();
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        if(mappers.get(MovesComponent.class, entity).movementDirection == 0)
            return;

        upToDateEntities.add(entity);

        if(!allEntities.contains(entity, true)) {
            playSound(entity);
            allEntities.add(entity);
        }

    }

    public void playSound(Entity entity) {
        MovingSoundComponent movingSoundC = mappers.get(MovingSoundComponent.class, entity);
        Sound sound = movingSoundC.sound;

        long id = sound.loop();
        movingSoundC.currentId = id;

        mappers.get(SoundComponent.class, entity).loopingSounds.add(PlayingSound.create().sound(sound).id(id).build());

        entity.add(getEngine().createComponent(SoundsComponent.class));
    }

    private void removeNotMoving() {
        allEntities.forEach(entity -> {
            if(!upToDateEntities.contains(entity, true)) {
                stopSound(entity);
                allEntities.removeValue(entity, true);
            }
        });
    }

    private void stopSound(Entity entity) {
        MovingSoundComponent movingSoundC = mappers.get(MovingSoundComponent.class, entity);
        Sound sound = movingSoundC.sound;

        Array<PlayingSound> loopingSounds = mappers.get(SoundComponent.class, entity).loopingSounds;
        loopingSounds.forEach(loopingSound -> {
            if(loopingSound.sound == sound) {
                loopingSounds.removeValue(loopingSound, true);
                fadeSound(loopingSound);
            }

        });
    }

    private void fadeSound(PlayingSound sound) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if(sound.volume >= FADE_STEP) {
                    sound.volume = sound.volume - FADE_STEP;
                    sound.sound.setVolume(sound.id, sound.volume);
                } else {
                    sound.sound.stop(sound.id);
                    this.cancel();
                }
            }
        }, 0f, FADE_STEP);
    }

}
