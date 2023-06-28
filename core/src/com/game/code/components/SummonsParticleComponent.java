package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class SummonsParticleComponent implements Component, Pool.Poolable {
    public String particleName;

    @Override
    public void reset() {
        particleName = "";
    }
}
