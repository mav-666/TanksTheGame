package com.game.code.systems.Sound;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.game.code.components.*;
import com.game.code.utils.Mappers;
import com.game.code.utils.PlayingSound;

public class SoundSystem extends IteratingSystem implements EntityListener {
    public static final Family FAMILY = Family.all(PlayerComponent.class, MobilityComponent.class, TransformComponent.class).get();
    private static final float AUDIBILITY = 10f;
    private static final float PITCH_RANGE = 0.15f;
    private static final float FADE_STEP = 0.25f;

    private Entity player;
    private final Vector2 playerPos = new Vector2();

    public SoundSystem() {
        super(Family.all(SoundsComponent.class, SoundComponent.class).get(), 98);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.addEntityListener(FAMILY, this);
    }

    @Override
    public void entityAdded(Entity entity) {
        player = entity;
    }

    @Override
    public void entityRemoved(Entity entity) {
        player = null;
    }

    @Override
    public void update(float deltaTime) {
        updatePlayerPosition();

        super.update(deltaTime);
    }

    private void updatePlayerPosition() {
        if(player == null)
            playerPos.set(0,0);
        else
            playerPos.set(Mappers.get(TransformComponent.class, player).position);
    }

    @Override
    protected void processEntity(Entity entity, float delta) {
        Vector2 sourcePos = Mappers.get(TransformComponent.class, entity).position;
        float volume = calcVolume(sourcePos);
        float pan = calcPan(sourcePos);

        SoundComponent soundC = Mappers.get(SoundComponent.class, entity);

        Array<PlayingSound> loopingSounds = soundC.loopingSounds;
        Array<PlayingSound> playingSounds = soundC.playingSounds;
        processLoopingSounds(loopingSounds, playingSounds, volume, pan);

        Array<Sound> sounds = soundC.sounds;
        playSingleSounds(sounds, volume, pan);

        playingSounds.clear();
        if(loopingSounds.isEmpty())
            entity.remove(SoundsComponent.class);
    }

    private void playSingleSounds(Array<Sound> sounds, float volume, float pan) {
        sounds.forEach((sound) -> {
            long id = sound.play();
            float pitch = calcPitch();

            playSoundFrom(sound, id, volume, pan, pitch);
        });

        sounds.clear();
    }

    private void processLoopingSounds(Array<PlayingSound> loopingSounds, Array<PlayingSound> playingSounds, float volume, float pan) {
        loopingSounds.forEach(sound -> {
            if(sound.id == -1)
                sound.id = sound.sound.loop();
            if(playingSounds.contains(sound, true)) {
                sound.volume = volume;
                sound.sound.setPan(sound.id, pan, volume);
            } else {
                fadeSound(sound);
                loopingSounds.removeValue(sound, true);
            }
        });
    }

    private void playSoundFrom(Sound sound, long id, float volume, float pan, float pitch) {
        sound.setPan(id, pan, volume);
        sound.setPitch(id, pitch);
    }

    private float calcVolume(Vector2 sourcePos) {

        float distance = playerPos.dst(sourcePos);

        float volume = 1 - distance/AUDIBILITY;

        if(volume < 0)
            volume = 0;

        return volume;
    }

    private float calcPan(Vector2 sourcePos) {
        float deltaX =  sourcePos.x - playerPos.x;

        if(deltaX < 1 && deltaX > -1)
            return 0;

        return 1/deltaX;
    }

    private float calcPitch() {
        return 1 + (float)((Math.random() * (2 * PITCH_RANGE)) - PITCH_RANGE);
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
