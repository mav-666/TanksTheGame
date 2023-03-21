package com.game.code.AssetManagment;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor;
import com.badlogic.gdx.utils.Pool;

public class ParticleEffectActorPool extends Pool<ParticleEffectActorPool.PooledEffectActor> {
    ParticleEffect prototype;

    ParticleEffectActorPool(ParticleEffect effect) {
        prototype = effect;
    }
    @Override
    protected PooledEffectActor newObject() {
        return new PooledEffectActor(this, new ParticleEffect(prototype), true);
    }

    @Override
    public PooledEffectActor obtain() {
        return super.obtain();

    }

    public class PooledEffectActor extends ParticleEffectActor {
        ParticleEffectActorPool associatedPool;

        public PooledEffectActor(ParticleEffectActorPool associatedPool,
                                 ParticleEffect particleEffect,
                                 boolean resetOnStart) {
            super(particleEffect, resetOnStart);
            this.setAutoRemove(true);
            this.associatedPool = associatedPool;
        }

        @Override
        public boolean remove() {
            this.free();
            return super.remove();
        }

        public void free() {
            associatedPool.free(this);
        }
    }
}
