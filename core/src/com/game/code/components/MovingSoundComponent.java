package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Pool;
import com.game.code.utils.PlayingSound;

public class MovingSoundComponent implements Component, Pool.Poolable {
    public Sound sound;
    public PlayingSound currentSound;

    @Override
    public void reset() {
        sound = null;
        currentSound = null;
    }
}
