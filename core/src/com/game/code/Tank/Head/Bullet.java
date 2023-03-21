package com.game.code.Tank.Head;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor;
import com.badlogic.gdx.utils.Pool;
import com.game.code.BodyData;
import com.game.code.Entity.BitCategories;
import com.game.code.BodyBuilder;
import com.game.code.Entity.Breakable;
import com.game.code.Entity.Entity;
import com.game.code.Entity.Projectile;
import com.game.code.TextureActor;

public class Bullet extends TextureActor implements Projectile, Pool.Poolable {
    private final World world;
    private Body body;

    private final short ownersGroupIndex;
    private final Shape bodyShape;

    private final Pool<Bullet> AssociatedPool;

    private final ParticleEffectActor shardEffect;

    private float damage;
    private float speed;

    protected Bullet(World world, ParticleEffectActor shardEffect, Pool<Bullet> pool, Entity owner, TextureRegion texture, float width, float damage, float speed) {
        super(texture);

        this.world = world;
        this.AssociatedPool = pool;
        this.shardEffect = shardEffect;

        Filter ownersFilters = owner.getBody().getFixtureList().first().getFilterData();
        if(ownersFilters.groupIndex >= 0)
            ownersFilters.groupIndex = (short) -((BodyData) owner.getBody().getUserData()).index;
        ownersGroupIndex = ownersFilters.groupIndex;

        this.damage = damage;
        this.speed = speed;

        setSize(width/8, width/8);
        setOrigin(getWidth()/2, getHeight()/2);

        bodyShape = new CircleShape();
        bodyShape.setRadius(width/18);

    }

    //angle in degrees
    public void init(Vector2 pos, float angle) {
        setPosition(pos.x, pos.y);
        setRotation(angle);

        body = BodyBuilder.createBody(world, this, pos.add(getWidth()/2, getHeight()/2), bodyShape, BodyDef.BodyType.DynamicBody, this.getCategory(), BitCategories.ALL.bit(), ownersGroupIndex,0.1f, 0, 0.1f);
        body.setTransform(body.getPosition(), (float) Math.toRadians(angle));

        body.setBullet(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        setRotation((float) Math.toDegrees(body.getAngle()));

        Vector2 velocity= new Vector2(0, speed);
        velocity.rotateRad(body.getAngle());

        this.body.setLinearVelocity(velocity);
    }

    @Override
    public void collusionRespond(Entity participant) {
        if(participant.getCategory() == BitCategories.AREA.bit())
            return;

        if(participant.getCategory() == BitCategories.BREAKABLE.bit()) {
            if(!((Breakable) participant).takeDamage(this.damage))
                return;
        }

        applyImpulseToParticipant(participant);

        flagForDispose();
    }

    private void applyShards() {
        shardEffect.setScale(1/(42f*8));
        shardEffect.setPosition(body.getPosition().x, body.getPosition().y);
        shardEffect.setSize(getWidth(), getHeight());
        shardEffect.start();
        if(getStage() != null)
            getStage().addActor(shardEffect);
    }

    private void applyImpulseToParticipant(Entity participant) {
        Vector2 impulse = new Vector2(0, speed/40f);
        impulse.y *= Math.abs(Math.cos(participant.getBody().getAngle() + Math.toRadians(getRotation())));
        impulse.rotateRad(getBody().getAngle());

        participant.getBody().applyLinearImpulse(impulse, getBody().getPosition(), true);
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public void reset() {}

    @Override
    public void destroy() {
        applyShards();
        Projectile.super.destroy();
        remove();
        AssociatedPool.free(this);
    }
}
