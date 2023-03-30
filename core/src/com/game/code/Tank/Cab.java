package com.game.code.Tank;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor;
import com.game.code.*;
import com.game.code.AssetManagment.AssetRequestParticlePools;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.AssetManagment.ParticleEffectActorPool;
import com.game.code.Entity.BitCategories;
import com.game.code.Entity.Breakable;

import java.util.*;

public class Cab extends TextureActor implements Breakable, AssetRequestParticlePools {
    private final Body body;

    private ParticleEffectActorPool traces;
    private ParticleEffectActorPool explosions;

    private float invincibilityTime;

    private float health;
    private float speed;
    private float mobility;

    protected Cab(
                  BodyHandler bodyHandler,
                  Vector2 pos,
                  float width, float height,
                  CabData cabData)
    {
        this.health = cabData.health;
        this.speed = cabData.speed;
        this.mobility = cabData.mobility;
        this.invincibilityTime = cabData.invincibilityTime;
        
        setSize(width, height);

        body = bodyHandler.requestCreation(this, pos.add(width/2, height/2), BodyDef.BodyType.DynamicBody);



        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth()/2, getHeight()/2);

        bodyHandler.createFixture(body, this, shape, this.getCategory(), BitCategories.ALL.bit(), false, 0.5f, 0.1f);

        body.getMassData().mass = 250;

        body.setLinearDamping(4f);
        body.setAngularDamping(2.5f);
    }

    @Override
    public void request(AssetRequestProcessor assetRequestProcessor) {
        assetRequestProcessor.receiveRequest("TanksTheGame.atlas", TextureAtlas.class, this);
        assetRequestProcessor.receiveRequest("trace", ParticleEffect.class, this);
        assetRequestProcessor.receiveRequest("explosion", ParticleEffect.class, this);
    }

    @Override
    public void passAssets(AssetRequestProcessor assets) {
        setTexture(assets.get("TanksTheGame.atlas", TextureAtlas.class).findRegion("cab"));
    }

    @Override
    public void passParticleAssets(AssetRequestProcessor assets, HashMap<ParticleEffect, ParticleEffectActorPool> particlePools) {
        traces = getParticlePool(particlePools, assets.get("trace", ParticleEffect.class));
        explosions = getParticlePool(particlePools, assets.get("explosion", ParticleEffect.class));
    }


    @Override
    public void act(float delta) {
        super.act(delta);

        if(body == null) return;

        this.getParent().setPosition(body.getPosition().x - getWidth()/2,
                body.getPosition().y - getHeight()/2);
        this.getParent().setRotation((float) Math.toDegrees(body.getAngle()));
    }

    public void move(float delta, MoveDirection direction) {
        body.setLinearVelocity(body.getLinearVelocity().add(
                new Vector2(0, (direction.mult == -1? speed* 0.75f : speed) * delta * direction.mult).rotateRad(body.getAngle())));
        applyTrace(direction);

    }

    private void applyTrace(MoveDirection direction) {
        if(getColor().a != 1)
            return;

        ParticleEffectActor trace1, trace2;

        trace1 = traces.obtain();
        trace2 = traces.obtain();

        trace1.setScale(1/(42f*4));
        trace2.setScale(1/(42f*4));

        trace1.setSize(getWidth(), getHeight());
        trace2.setSize(getWidth(), getHeight());

        trace1.setPosition(getWidth() / 10,
                getHeight() / 10 * direction.mult + Math.max(0, getHeight() * -direction.mult));
        trace2.setPosition(getWidth() - getWidth() / 10,
                getHeight() / 10 * direction.mult + Math.max(0, getHeight() * -direction.mult));

        trace1.getEffect().getEmitters().first().getAngle().setHigh(-90 * direction.mult);
        trace2.getEffect().getEmitters().first().getAngle().setHigh(-90 * direction.mult);

        trace1.start();
        trace2.start();

        getParent().addActorBefore(this, trace1);
        getParent().addActorBefore(this, trace2);
    }

    public void turn(float delta, TurnDirection direction) {
        body.setAngularVelocity(body.getAngularVelocity() + mobility * delta * direction.mult);
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public boolean takeDamage(float damage) {
        if(health <= 0 ) {
            return true;
        }

        health -= damage;

        ((Actor) ((BodyData) body.getUserData()).owner).addAction(Animations.damaged(invincibilityTime));

        body.getFixtureList().first().getFilterData().maskBits = (short) ~BitCategories.PROJECTILE.bit();

        addAction(Actions.delay(invincibilityTime, Actions.run(() -> {
            body.getFixtureList().first().getFilterData().maskBits = BitCategories.ALL.bit();
            if(health <= 0)
                die();
        })));

        return true;
    }

    @Override
    public void die() {

        //Anim
        ((Actor) ((BodyData) body.getUserData()).owner).addAction(Actions.delay(invincibilityTime/1000f, Actions.color(Color.valueOf("#646464"), 0.5f)));
        getBody().setFixedRotation(false);
        getBody().setAngularDamping(12f);
        getParent().setTouchable(Touchable.disabled);
        getStage().addActor(new Explosion(((BodyData) body.getUserData()).bodyHandler, explosions.obtain(), getBody().getPosition(), getWidth(), 50));
    }
}
