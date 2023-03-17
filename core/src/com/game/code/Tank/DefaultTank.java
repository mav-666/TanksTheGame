package com.game.code.Tank;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.Tank.Head.Canon;

import java.util.HashMap;
import java.util.HashSet;


public class DefaultTank extends Tank implements AssetRequest {

    private final static long INVINCIBILITY_TIME = 500;

    private final static float HEALTH = 150f;
    private final static float SPEED = 12f;
    private final static float MOBILITY = 1.5f;

    private final static float DAMAGE = 50;
    private final static float ROTATION_SPEED = 75F;
    private final static float PROJECTILE_SPEED = 6f;
    private final static float RECOIL = 0.24f;
    private final static long RECHARGE = 750;

    public DefaultTank(World world, Vector2 pos, float width, float height) {
        super(pos, width, height);

        this.setCab(new Cab(world, pos, width, height, HEALTH, SPEED, MOBILITY, INVINCIBILITY_TIME));
        this.setHead(new Canon(world,this, width, height, ROTATION_SPEED, DAMAGE, PROJECTILE_SPEED, RECOIL, RECHARGE));
    }

    @Override
    public void request(HashMap<String, Class<?>> requests, HashSet<AssetRequest> clients) {
        cab.request(requests, clients);
        head.request(requests, clients);
    }

    @Override
    public void passAssets(AssetRequestProcessor assets) {

    }
}
