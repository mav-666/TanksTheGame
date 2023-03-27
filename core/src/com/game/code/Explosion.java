package com.game.code;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor;
import com.game.code.Entity.Area;
import com.game.code.Entity.BitCategories;
import com.game.code.Entity.Breakable;
import com.game.code.Entity.Entity;

public class Explosion extends Actor implements Area {
    private Body body;

    private ParticleEffectActor explosionEffect;

    private float damage;

    Explosion(World world, ParticleEffectActor explosionEffect, Vector2 pos, float radius, float damage) {
        this.explosionEffect = explosionEffect;
        this.damage = damage;

        CircleShape bodyShape = new CircleShape();
        bodyShape.setRadius(radius);

        body = BodyBuilder.createBody(world, this, pos.add(getWidth()/2, getHeight()/2), BodyDef.BodyType.DynamicBody, 0.1f);
        BodyBuilder.createFixture(body, this, bodyShape, this.getCategory(), BitCategories.ALL.bit(),0.1f, 0);

        body.getFixtureList().first().setSensor(true);
    }

    private void applyExplosionEffect() {
        explosionEffect.setPosition(body.getPosition().x, body.getPosition().y);
        explosionEffect.start();
        if(this.getStage() != null)
            getStage().addActor(explosionEffect);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        flagForDispose();
    }

    @Override
    public void collusionRespond(Entity participant) {
        if(participant instanceof Breakable) ((Breakable) participant).takeDamage(damage);
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public void destroy() {
        applyExplosionEffect();
        Area.super.destroy();
    }
}
