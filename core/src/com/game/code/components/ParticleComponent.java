package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.Pool;

public class ParticleComponent implements Component, Pool.Poolable {
    public ParticleEffect particleEffect;

    @Override
    public void reset() {
        particleEffect = null;
    }
}
