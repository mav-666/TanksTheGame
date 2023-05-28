package com.game.code.BattleFieldFineVersion;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor;
import com.game.code.AssetManagment.AssetProcessor;
import com.game.code.AssetManagment.AssetRequestParticlePools;
import com.game.code.AssetManagment.ParticleEffectActorPool;
import com.game.code.Entity.BitCategories;
import com.game.code.Entity.Breakable;
import com.game.code.utils.Effects.Explosion;
import com.game.code.utils.box2d.BodyData;
import com.game.code.utils.box2d.BodyHandler;
import com.game.code.utils.scene2d.Animations;

import java.util.HashMap;

public class GasolineFactory implements ObstacleFactory {


    @Override
    public Obstacle create(BodyHandler bodyHandler, Vector2 pos, float width, float height) {
        return new Gasoline(bodyHandler, pos, width);
    }

    private class Gasoline extends Obstacle implements Breakable, AssetRequestParticlePools {
        private ParticleEffectActor explosionEffect;
        float health;

        private Gasoline(BodyHandler bodyHandler, Vector2 pos, float radius) {
            super(BitCategories.BREAKABLE, pos, radius, radius);

            health = 100;

            body = bodyHandler.requestCreation(this, pos.add(radius/2, radius/2), BodyDef.BodyType.DynamicBody);

            CircleShape shape = new CircleShape();
            shape.setRadius(getWidth()/2);

            bodyHandler.createFixture(body, this, shape, this.getCategory(), BitCategories.ALL.bit(), false, 0.5f, 0.1f);

            body.setFixedRotation(false);

            body.getMassData().mass = 75;

            body.setAngularDamping(6);
            body.setLinearDamping(6);
        }

        @Override
        public void request(AssetProcessor assetRequestProcessor) {
            assetRequestProcessor.receiveRequest("explosion", ParticleEffect.class, this);
        }

        @Override
        public void passAssets(AssetProcessor assets) {}

        @Override
        public void passParticleAssets(AssetProcessor assets, HashMap<ParticleEffect, ParticleEffectActorPool> particlePools) {
            explosionEffect = getParticlePool(particlePools, assets.get("explosion", ParticleEffect.class)).obtain();
        }

        @Override
        public float getHealth() {
            return health;
        }

        @Override
        public boolean takeDamage(float damage) {
            if(health <= 0) {
                return true;
            }

            health -= damage;

            if(health <= 0) {
                die();
                return true;
            }

            ((Actor) ((BodyData) body.getUserData()).owner).addAction(Animations.damaged(0.5f));

            return true;
        }

        @Override
        public void die() {

            addAction(Actions.sequence(
                    Actions.parallel(
                            Actions.scaleTo(getScaleX(), 0, 0.5f),
                            Actions.fadeOut(0.5f)),
                    Actions.run(() -> (
                            (BodyData) getBody().getUserData()).bodyHandler.requestDisposal((((BodyData)getBody().getUserData()).owner)))));

            getStage().addActor(new Explosion(((BodyData) getBody().getUserData()).bodyHandler, explosionEffect, body.getPosition(), getWidth()*1.5f, 75));
        }

        @Override
        public void destroy() {
            super.destroy();
            remove();
        }
    }
}
