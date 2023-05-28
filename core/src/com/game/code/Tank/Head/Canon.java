package com.game.code.Tank.Head;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.game.code.Tank.ProjectileSupplier;
import com.game.code.utils.scene2d.Animations;
import com.game.code.utils.scene2d.TexturedActor;

import java.util.*;

public class Canon extends Group implements Head {
    private final ProjectileSupplier projectile;

    private boolean isRecharging;

    private float rotationSpeed;
    private float rechargeTime;

    protected TexturedActor head;
    protected TexturedActor barrel;

    public Canon(ProjectileSupplier projectile, float rotationSpeed, float rechargeTime,
                 TextureRegion headTexture, TextureRegion barrelTexture) {
        this.projectile = projectile;

        head = new TexturedActor(headTexture);
        barrel = new TexturedActor(barrelTexture);

                //new BulletPool(owner, width, headData.damage, headData.projectileSpeed);

        addActor(barrel);
        addActor(head);
    }

    @Override
    public void aim(float delta, Vector2 aimPosition) {

    }

    @Override
    public void shoot() {
        if(isRecharging)
            return;

        //anim
        barrel.addAction(Animations.recoil((barrel.getHeight()/3)));
        head.addAction(Actions.delay(0.1f, Animations.recoil((head.getHeight()/5 * recoilStrength()))));

        projectile.spawnProjectile(calculateBulletPosition(), getRotation() + getRotation());

        getStage().getRoot().addActor(bullet);

        recoil(bullet);

        recharge();
    }

    //rotateBy(headData.rotationSpeed * delta * direction.mult);

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

    @Override
    public void sizeChanged() {
        head.setSize(getWidth()/2, getHeight()/2);
        head.setPosition(getWidth()/2 - head.getWidth()/2, getHeight()/2 - head.getHeight()/2);


        barrel.setSize(head.getWidth()/3, head.getHeight()*1.25f);
        barrel.setPosition(getWidth()/2 - barrel.getWidth()/2, getHeight()/2);
        barrel.setOrigin(barrel.getX() + barrel.getWidth()/2, 0);
    }
}
