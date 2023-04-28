package com.game.code.utils.Effects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor;
import com.game.code.Entity.*;
import com.game.code.utils.box2d.BodyData;
import com.game.code.utils.box2d.BodyHandler;

public class Explosion extends Actor implements Area, DelayedEntity {
    private Body body;

    private final ParticleEffectActor explosionEffect;
    private final BodyHandler bodyHandler;

    private float damage;

    public Explosion(BodyHandler bodyHandler, ParticleEffectActor explosionEffect, Vector2 pos, float radius, float damage) {
        this.bodyHandler = bodyHandler;
        this.explosionEffect = explosionEffect;
        this.damage = damage;

        setPosition(pos.x - getWidth()/2, pos.y - getWidth()/2);
        setSize(radius, radius);

        bodyHandler.requestCreation( this, pos, BodyDef.BodyType.DynamicBody);
    }

    @Override
    public void initBody(Body body, BodyHandler bodyHandler) {
        Shape bodyShape = new CircleShape();
        bodyShape.setRadius(getWidth());

        bodyHandler.createFixture(body, this, bodyShape, this.getCategory(), BitCategories.ALL.bit(), true,0.1f, 0);

        this.body = body;

        apply();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(explosionEffect.getEffect().getEmitters().first().getPercentComplete() > 0.5 && body != null)
            ((BodyData) body.getUserData()).bodyHandler.requestDisposal(this);
    }

    private void apply() {
        explosionEffect.setScale(1/(42f*2));
        explosionEffect.setPosition(getBody().getPosition().x, getBody().getPosition().y);
        explosionEffect.setSize(getWidth(), getHeight());
        explosionEffect.start();
        getParent().addActorBefore(this, explosionEffect);
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
