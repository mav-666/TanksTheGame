package com.game.code.Tank.Head;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.game.code.AssetManagment.AssetProcessor;
import com.game.code.utils.scene2d.Animations;
import com.game.code.utils.box2d.BodyHandler;
import com.game.code.Entity.Entity;

import java.util.*;

public class Canon extends Head {
    private final BodyHandler bodyHandler;
    private final BulletPool bulletPool;

    private boolean isRecharging;

    public Canon(BodyHandler bodyHandler,
                 Entity owner,
                 float width, float height,
                 HeadData headData)
    {
        super(width, height, owner, headData);
        this.bodyHandler = bodyHandler;

        setWidth(width);

        bulletPool = new BulletPool(owner, width, headData.damage, headData.projectileSpeed);
        
        this.isRecharging= false;
    }

    @Override
    public void request(AssetProcessor assetRequestProcessor) {
        assetRequestProcessor.receiveRequest("TanksTheGame.atlas", TextureAtlas.class, this);

        bulletPool.request(assetRequestProcessor);
    }

    @Override
    public void passAssets(AssetProcessor assets) {
        TextureAtlas atlas = assets.get("TanksTheGame.atlas", TextureAtlas.class);
        barrel.setTexture(atlas.findRegion("barrel"));
        head.setTexture(atlas.findRegion("canon"));
    }

    @Override
    public void shoot() {
        if(isRecharging)
            return;

        //anim
        barrel.addAction(Animations.recoil((barrel.getHeight()/3)));
        head.addAction(Actions.delay(0.1f, Animations.recoil((head.getHeight()/5 * recoilStrength()))));

        Bullet bullet = bulletPool.obtain();
        bullet.init(bodyHandler, calculateBulletPosition(), getParent().getRotation() + getRotation());

        getStage().getRoot().addActor(bullet);
        recoil(bullet);
        recharge();
    }

    private void recoil(Bullet bullet) {
        Vector2 impulse = new Vector2(0, -headData.recoil);
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
        }, headData.recharge);
    }

    private Vector2 calculateBulletPosition() {
        float angle = getParent().getRotation() + getRotation();

        Vector2 barrelVector = new Vector2(0,
                getParent().getY() + barrel.getY() + barrel.getHeight()
                        - (getParent().getY() + getParent().getHeight()/2));

        barrelVector.rotateDeg(angle);

        return new Vector2(getParent().getX() + getParent().getWidth()/2 - getParent().getWidth()/16,
                getParent().getY() + getParent().getHeight()/2 - getParent().getWidth()/16).add(barrelVector);

    }
}
