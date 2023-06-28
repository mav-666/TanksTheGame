package com.game.code.EntityBuilding.FieldInitializers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ParticleEffectInitializer extends FieldInitializer<ParticleEffect, String> {

    @Override
    public ParticleEffect getInitBy(String config) {
        ParticleEffect particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal(config),
                new TextureAtlas(Gdx.files.internal("Particles.atlas")));

        return particleEffect;
    }
}
