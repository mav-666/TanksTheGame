package com.game.code.Tank.Head;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.game.code.Animations;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestParticlePools;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.Entity.Entity;

import java.util.*;

public class Canon extends Head {
    private final Entity owner;

    private final BulletPool bulletPool;

    protected float recoil;
    protected long recharge;

    private boolean isRecharging;

    public Canon(World world, Entity owner, float width, float height, float rotationSpeed, float damage, float projectileSpeed, float recoil, long recharge) {
        super(width, height, rotationSpeed);
        this.owner= owner;

        setWidth(width);

        bulletPool = new BulletPool(world, owner, width, damage, projectileSpeed);

        this.recoil = recoil;
        this.recharge = recharge;

        this.isRecharging= false;
    }

    @Override
    public void passAssets(AssetRequestProcessor assets) {
        TextureAtlas atlas = assets.get("TanksTheGame.atlas", TextureAtlas.class);
        barrel.setTexture(atlas.findRegion("barrel"));
        head.setTexture(atlas.findRegion("head"));
    }

    @Override
    public void request(HashMap<String, Class<?>> requests, HashSet<AssetRequest> clients) {
        addRequest(requests, clients, "TanksTheGame.atlas", TextureAtlas.class, this);
        bulletPool.request(requests, clients);
    }

    @Override
    public void shoot() {
        if(isRecharging)
            return;

        //anim
        barrel.addAction(Animations.recoil((barrel.getHeight()/3)));
        head.addAction(Actions.delay(0.1f, Animations.recoil((head.getHeight()/5 * recoilStrength()))));

        Bullet bullet = bulletPool.obtain();
        bullet.init(calculateBulletPosition(), getParent().getRotation() + getRotation());

        getStage().getRoot().addActorBefore(barrel.getParent().getParent(), bullet);
        recoil(bullet);
        recharge();
    }

    private void recoil(Bullet bullet) {
        Vector2 impulse = new Vector2(0, -recoil);
        impulse.y *= recoilStrength();
        impulse.rotateRad(bullet.getBody().getAngle());

        owner.getBody().applyLinearImpulse(impulse, bullet.getBody().getPosition(), true);
    }

    private float recoilStrength() {
        return (float) Math.abs(Math.cos(Math.toRadians(getRotation()))) + 0.1f;
    }

    private void recharge() {
        isRecharging = true;

        Timer timer= new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isRecharging = false;
            }
        }, recharge);
    }

    private Vector2 calculateBulletPosition() {
        float angle = getParent().getRotation() + getRotation();

        Vector2 barrelVector = new Vector2(0, getParent().getY() + barrel.getY() + barrel.getHeight() - (getParent().getY() + getParent().getHeight()/2));

        barrelVector.rotateDeg(angle);

        return new Vector2(getParent().getX() + getParent().getWidth()/2 - getParent().getWidth()/16, getParent().getY() + getParent().getHeight()/2 - getParent().getWidth()/16).add(barrelVector);

    }
}
