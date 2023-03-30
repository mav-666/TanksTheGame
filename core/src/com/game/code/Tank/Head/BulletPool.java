package com.game.code.Tank.Head;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.game.code.AssetManagment.AssetRequestParticlePools;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.AssetManagment.ParticleEffectActorPool;
import com.game.code.BodyHandler;
import com.game.code.Entity.Entity;

import java.util.HashMap;


public class BulletPool extends Pool<Bullet> implements AssetRequestParticlePools {

    private final Entity owner;

    private TextureRegion bulletTexture;
    private ParticleEffectActorPool shards;

    private final float bulletWidth;
    private float bulletDamage;
    private float bulletSpeed;


    BulletPool(Entity owner, float width, float damage, float speed) {
        this.owner = owner;

        this.bulletWidth = width;
        this.bulletDamage = damage;
        this.bulletSpeed = speed;
    }

    @Override
    public void request(AssetRequestProcessor assetRequestProcessor) {
        assetRequestProcessor.receiveRequest("TanksTheGame.atlas", TextureAtlas.class, this);
        assetRequestProcessor.receiveRequest("shards", ParticleEffect.class, this);
    }

    @Override
    public void passAssets(AssetRequestProcessor assets) {
        bulletTexture = assets.get("TanksTheGame.atlas", TextureAtlas.class).findRegion("bullet");
    }

    @Override
    public void passParticleAssets(AssetRequestProcessor assets, HashMap<ParticleEffect, ParticleEffectActorPool> particlePools) {
        shards = getParticlePool(particlePools, assets.get("shards", ParticleEffect.class));
    }


    @Override
    protected Bullet newObject() {
        return new Bullet(shards.obtain(), this, owner, bulletTexture, bulletWidth, bulletDamage, bulletSpeed);
    }

    public void setBulletDamage(float bulletDamage) {
        this.bulletDamage = bulletDamage;
    }

    public void setBulletSpeed(float bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }


}
