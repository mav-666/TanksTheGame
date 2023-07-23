package com.game.code.EntityBuilding.FieldInitializers;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.game.code.systems.RenderingSystem;
import com.game.code.utils.Assets.Assets;
import com.game.code.utils.Assets.Particle;

public class AssetParticleInitializer extends FieldInitializer<ParticleEffect, String> {

    private final Assets assets;

    public AssetParticleInitializer(Assets assets) {
        this.assets = assets;
    }

    @Override
    public ParticleEffect getInitBy(String config) {
        return assets.getParticle(Particle.valueOf(config));
    }
}
