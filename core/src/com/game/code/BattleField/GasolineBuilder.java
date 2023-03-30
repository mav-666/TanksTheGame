package com.game.code.BattleField;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor;
import com.badlogic.gdx.utils.Array;
import com.game.code.*;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestParticlePools;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.AssetManagment.ParticleEffectActorPool;
import com.game.code.Entity.BitCategories;
import com.game.code.Entity.Breakable;

import java.util.HashMap;

public class GasolineBuilder extends ObstacleBuilder implements AssetRequest {
    public GasolineBuilder(float density, BattleFieldBuilder battleFieldBuilder) {
        super(density, battleFieldBuilder);
    }

    @Override
    public void request(AssetRequestProcessor assetRequestProcessor) {
        super.request(assetRequestProcessor);

        assetRequestProcessor.receiveRequest("TanksTheGame.atlas", TextureAtlas.class, this);

        obstacles.getChildren().forEach(((actor -> ((AssetRequest) actor).request(assetRequestProcessor))));
    }

    @Override
    public void passAssets(AssetRequestProcessor assets) {
        super.passAssets(assets);

        Array<TextureAtlas.AtlasRegion> tileTextures = assets.get("TanksTheGame.atlas", TextureAtlas.class).findRegions("gasoline");

        obstacles.getChildren().forEach((actor) ->
                ((TextureActor) actor).setTexture((tileTextures.get(getRandom().nextInt(tileTextures.size)))));

    }

    @Override
    protected void addObstacle() {
        Vector2 space = getFreeSpace().stream().toList().get(getRandom().nextInt(getFreeSpace().size()));
        getFreeSpace().remove(space);

        float width = getRandom().nextFloat() * (getBattleFieldTileWidth() - 0.5f) + 0.5f;

        obstacles.addActor(createObstacle(space, width, width));
    }

    protected Obstacle createObstacle(Vector2 pos, float width, float height) {
        return new Gasoline(getBattleFieldBodyHandler(), pos, width);
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
        public void request(AssetRequestProcessor assetRequestProcessor) {
            assetRequestProcessor.receiveRequest("explosion", ParticleEffect.class, this);
        }

        @Override
        public void passAssets(AssetRequestProcessor assets) {}

        @Override
        public void passParticleAssets(AssetRequestProcessor assets, HashMap<ParticleEffect, ParticleEffectActorPool> particlePools) {
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
