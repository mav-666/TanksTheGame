package com.game.code.Tank;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.BodyData;
import com.game.code.Tank.Head.Canon;


public class DefaultTank extends Tank {

    private final static long INVINCIBILITY_TIME = 500;

    private final static float HEALTH = 150f;
    private final static float SPEED = 12f;
    private final static float MOBILITY = 1.5f;

    private final static float DAMAGE = 50;
    private final static float ROTATION_SPEED = 75F;
    private final static float PROJECTILE_SPEED = 6f;
    private final static float RECOIL = 0.75f;
    private final static long RECHARGE = 750;

    public DefaultTank( AssetRequestProcessor assetRequestProcessor,
                        World world,
                        Vector2 pos,
                        float width, float height)
    {
        super(pos, width, height);

        this.setCab(new Cab(assetRequestProcessor, world, pos, width, height,
                HEALTH, SPEED, MOBILITY, INVINCIBILITY_TIME));
        ((BodyData) cab.getBody().getUserData()).owner = this;
        this.setHead(new Canon(assetRequestProcessor, world, this, width, height,
                ROTATION_SPEED, DAMAGE, PROJECTILE_SPEED, RECOIL, RECHARGE));
    }
}
