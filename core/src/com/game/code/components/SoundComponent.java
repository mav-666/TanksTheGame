package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.game.code.utils.PlayingSound;

public class SoundComponent implements Component, Pool.Poolable {
    public Array<Sound> sounds = new Array<>();
    public Array<PlayingSound> loopingSounds = new Array<>();
    public Array<Sound> stoppedSound = new Array<>();

    @Override
    public void reset() {
        sounds.clear();
    }
}
