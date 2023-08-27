package com.game.code.utils;

import com.badlogic.gdx.audio.Sound;

public class PlayingSound {
    public Sound sound;
    public long id;
    public float volume;
    public boolean isPaused;

    private PlayingSound(Builder builder) {
        sound = builder.sound;
        id = builder.id;
        volume  = builder.volume;
        isPaused = builder.isPaused;
    }

    public static Builder create() {
        return new Builder();
    }

    public static final class Builder {
        private Sound sound;
        private long id = -1;
        private float volume = 0;
        private boolean isPaused = true;

        private Builder() {
        }

        public Builder sound(Sound val) {
            sound = val;
            return this;
        }

        public Builder id(long val) {
            id = val;
            return this;
        }

        public Builder volume(float val) {
            volume = val;
            return this;
        }

        public Builder volume(boolean val) {
            isPaused = val;
            return this;
        }

        public PlayingSound build() {
            return new PlayingSound(this);
        }
    }
}
