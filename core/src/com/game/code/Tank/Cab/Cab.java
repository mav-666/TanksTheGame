package com.game.code.Tank.Cab;

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
import com.game.code.AssetManagment.AssetProcessor;
import com.game.code.AssetManagment.AssetRequestParticlePools;
import com.game.code.AssetManagment.ParticleEffectActorPool;
import com.game.code.Entity.BitCategories;
import com.game.code.Entity.Breakable;
import com.game.code.utils.Effects.*;
import com.game.code.utils.box2d.BodyData;
import com.game.code.utils.box2d.BodyHandler;
import com.game.code.utils.scene2d.Animations;
import com.game.code.utils.scene2d.TextureActor;

import java.util.*;

public class Cab extends TextureActor implements Breakable, AssetRequestParticlePools {
    private final Body body;

    private final TracePool traces;
    private ParticleEffectActorPool explosions;

    private final CabData cabData;

    protected Cab(
            BodyHandler bodyHandler,
            Vector2 pos,
            float width, float height,
            CabData cabData) {
        this.cabData = cabData;

        setSize(width, height);

        body = bodyHandler.requestCreation(this, pos.add(width / 2, height / 2), BodyDef.BodyType.DynamicBody);


        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2, getHeight() / 2);

        bodyHandler.createFixture(body, this, shape, this.getCategory(), BitCategories.ALL.bit(), false, 0.5f, 0.1f);

        body.getMassData().mass = 250;

        body.setLinearDamping(4f);
        body.setAngularDamping(2.5f);

        traces = new TracePool();
    }

    @Override
    public void request(AssetProcessor assetRequestProcessor) {
        assetRequestProcessor.receiveRequest("TanksTheGame.atlas", TextureAtlas.class, this);
        assetRequestProcessor.receiveRequest("explosion", ParticleEffect.class, this);

        traces.request(assetRequestProcessor);
    }

    @Override
    public void passAssets(AssetProcessor assets) {
        setTexture(assets.get("TanksTheGame.atlas", TextureAtlas.class).findRegion("cab"));
    }

    @Override
    public void passParticleAssets(AssetProcessor assets, HashMap<ParticleEffect, ParticleEffectActorPool> particlePools) {
        explosions = getParticlePool(particlePools, assets.get("explosion", ParticleEffect.class));
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (body == null) return;

        this.getParent().setPosition(body.getPosition().x - getWidth() / 2,
                body.getPosition().y - getHeight() / 2);
        this.getParent().setRotation((float) Math.toDegrees(body.getAngle()));
    }

    public void move(float delta, MoveDirection direction) {
        body.setLinearVelocity(body.getLinearVelocity().add(
                new Vector2(0, (direction.mult == -1 ? cabData.speed * 0.75f : cabData.speed) * delta * direction.mult).rotateRad(body.getAngle())));
        applyTraces(direction);

    }

    private void applyTraces(MoveDirection direction) {
        if (getColor().a != 1)
            return;

        applyTrace(getWidth() / 10,
                getHeight() / 10 * direction.mult + Math.max(0, getHeight() * -direction.mult), direction);

        applyTrace(getWidth() - getWidth() / 10,
                getHeight() / 10 * direction.mult + Math.max(0, getHeight() * -direction.mult), direction);
    }

    private void applyTrace(float x, float y, MoveDirection direction) {

        Trace trace = traces.obtain();

        trace.setSize(getWidth(), getHeight());

        trace.setPosition(x, y);

        trace.setRotation(-90 * direction.mult);

        getParent().addActorBefore(this, trace);
    }

    public void turn(float delta, TurnDirection direction) {
        body.setAngularVelocity(body.getAngularVelocity() + cabData.mobility * delta * direction.mult);
    }

    public CabData getCabData() {
        return cabData;
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public float getHealth() {
        return cabData.health;
    }

    @Override
    public boolean takeDamage(float damage) {
        if (cabData.health <= 0) {
            return true;
        }

        cabData.health -= damage;

        ((Actor) ((BodyData) body.getUserData()).owner).addAction(Animations.damaged(cabData.invincibilityTime));

        body.getFixtureList().first().getFilterData().maskBits = (short) ~BitCategories.PROJECTILE.bit();

        addAction(Actions.delay(cabData.invincibilityTime, Actions.run(() -> {
            body.getFixtureList().first().getFilterData().maskBits = BitCategories.ALL.bit();
            if (cabData.health <= 0)
                die();
        })));

        return true;
    }

    @Override
    public void die() {

        //Anim
        ((Actor) ((BodyData) body.getUserData()).owner).addAction(Actions.delay(cabData.invincibilityTime / 1000f, Actions.color(Color.valueOf("#646464"), 0.5f)));
        getBody().setFixedRotation(false);
        getBody().setAngularDamping(12f);
        getParent().setTouchable(Touchable.disabled);

        getStage().addActor(new Explosion(((BodyData) body.getUserData()).bodyHandler, explosions.obtain(), getBody().getPosition(), getWidth(), 50));
    }
}
