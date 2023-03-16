package com.game.code.Tank;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor;
import com.game.code.Animations;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestParticlePools;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.AssetManagment.ParticleEffectActorPool;
import com.game.code.Entity.BitCategories;
import com.game.code.BodyBuilder;
import com.game.code.Entity.Breakable;
import com.game.code.TextureEntity;

import java.util.*;

public class Cab extends TextureEntity implements Breakable, AssetRequestParticlePools {
    private final Body body;

    private ParticleEffectActorPool traces;

    private long invincibilityTime;

    private float health;
    private float speed;
    private float mobility;

    protected Cab(World world, Vector2 pos, float width, float height,
                  float health, float speed, float mobility, long invisibilityTime) {

        this.health = health;
        this.speed = speed;
        this.mobility = mobility;
        this.invincibilityTime = invisibilityTime;
        
        setSize(width, height);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width/2, height/2);

        body = BodyBuilder.createBody(world, this, pos.add(width/2, height/2), polygonShape,
                BodyDef.BodyType.DynamicBody, this.getCategory(), BitCategories.ALL.bit(),0.1f, 0.1f);

        body.setLinearDamping(4f);
        body.setAngularDamping(2.5f);

    }

    @Override
    public void passAssets(AssetRequestProcessor assets) {
        setTexture(assets.get("TanksTheGame.atlas", TextureAtlas.class).findRegion("body"));
    }

    @Override
    public void passParticleAssets(AssetRequestProcessor assets, HashMap<ParticleEffect, ParticleEffectActorPool> particlePools) {
        traces = getParticlePool(particlePools, assets.get("trace", ParticleEffect.class));
    }

    @Override
    public void request(HashMap<String, Class<?>> requests, HashSet<AssetRequest> clients) {
        addRequest(requests,  clients, "TanksTheGame.atlas", TextureAtlas.class, this);
        addRequest(requests, clients, "trace", ParticleEffect.class, this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        this.getParent().setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        this.getParent().setRotation((float) Math.toDegrees(body.getAngle()));
    }

    //тест повреждений
//    private void processInput(float delta) {
//        if(Gdx.input.isKeyJustPressed(Input.Keys.G)) {
//            body.setLinearDamping(1.5f);
//            speed /= 4;
//            speed *= 1.5;
//        }
//
//        if(Gdx.input.isKeyJustPressed(Input.Keys.H)) {
//            body.setLinearDamping(4f);
//            speed *= 4;
//            speed /= 1.5;
//        }
//
//        if(Gdx.input.isKeyJustPressed(Input.Keys.B)) {
//            body.setAngularDamping(0.625f);
//            mobility /= 4;
//        }
//
//        if(Gdx.input.isKeyJustPressed(Input.Keys.N)) {
//            body.setAngularDamping(2.5f);
//            mobility *= 4;
//        }
//
//    }

    public void move(float delta, MoveDirection direction) {
        body.setLinearVelocity(body.getLinearVelocity().add(new Vector2(0, speed * delta * direction.mult).rotateRad(body.getAngle())));
        applyTrace(direction);

    }

    private void applyTrace(MoveDirection direction) {
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
        health -= damage;
        getParent().addAction(Animations.damaged(invincibilityTime));

        body.getFixtureList().first().getFilterData().maskBits = (short) ~BitCategories.PROJECTILE.bit();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                body.getFixtureList().first().getFilterData().maskBits = BitCategories.ALL.bit();
                if(health <= 0) {
                    die();
                }
            }
        },  invincibilityTime);
        return true;
    }

    @Override
    public void die() {

        //Anim
        getParent().addAction(Actions.color(Color.valueOf("1a453b"), 1f));

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                getParent().setTouchable(Touchable.disabled);
            }
        },  100);
    }
}
