package com.game.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor;
import com.game.code.Entity.*;

public class Explosion extends Actor implements Area, DelayedEntity {
    private Body body;

    private final ParticleEffectActor explosionEffect;

    private final float damage;

    public Explosion(BodyHandler bodyHandler, ParticleEffectActor explosionEffect, Vector2 pos, float radius, float damage) {
        this.explosionEffect = explosionEffect;
        this.damage = damage;

        setPosition(pos.x - radius/2, pos.y - radius/2);
        setSize(radius, radius);

        bodyHandler.requestCreation( this, pos, BodyDef.BodyType.DynamicBody);
    }

    @Override
    public void initBody(Body body, BodyHandler bodyHandler) {
        CircleShape bodyShape = new CircleShape();
        bodyShape.setRadius(getWidth());

        bodyHandler.createFixture(body, this, bodyShape, this.getCategory(), BitCategories.ALL.bit(), true,0.1f, 0);

        this.body = body;

        applyExplosionEffect();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(explosionEffect.getEffect().getEmitters().first().getPercentComplete() > 0.5 && body != null)
            ((BodyData) body.getUserData()).bodyHandler.requestDisposal(this);
    }

    private void applyExplosionEffect() {
        explosionEffect.setScale(1/(42f*2));
        explosionEffect.setPosition(getBody().getPosition().x, getBody().getPosition().y);
        explosionEffect.setSize(getWidth(), getHeight());
        explosionEffect.start();
        if(this.getStage() != null)
            getStage().addActor(explosionEffect);
    }

    @Override
    public void collusionRespond(Entity participant) {
        if(participant.getCategory() == BitCategories.BREAKABLE.bit())
            ((Breakable) participant).takeDamage(damage);
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public void destroy() {
        Area.super.destroy();
        remove();
    }
}
