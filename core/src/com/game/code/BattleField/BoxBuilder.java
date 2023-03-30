package com.game.code.BattleField;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.BodyHandler;
import com.game.code.Entity.BitCategories;
import com.game.code.TextureActor;

public class BoxBuilder extends ObstacleBuilder implements AssetRequest {

    public BoxBuilder(float density, BattleFieldBuilder battleFieldBuilder) {
        super(density, battleFieldBuilder);
    }

    @Override
    public void request(AssetRequestProcessor assetRequestProcessor) {
        super.request(assetRequestProcessor);

        assetRequestProcessor.receiveRequest("TanksTheGame.atlas", TextureAtlas.class, this);
    }

    @Override
    public void passAssets(AssetRequestProcessor assets) {
        Array<TextureAtlas.AtlasRegion> tileTextures =
                assets.get("TanksTheGame.atlas", TextureAtlas.class).findRegions("box");

        obstacles.getChildren().forEach((actor) ->
                ((TextureActor) actor).setTexture((tileTextures.get(getRandom().nextInt(tileTextures.size)))));
    }

    @Override
    protected void addObstacle() {
        Vector2 space = getFreeSpace().stream().toList().get(getRandom().nextInt(getFreeSpace().size()));
        getFreeSpace().remove(space);

        float width = getRandom().nextFloat() * (getBattleFieldTileWidth() - 0.5f) + 0.5f;
        float height = getRandom().nextFloat() * (getBattleFieldTileHeight() - 0.5f) + 0.5f;

        space.add((getBattleFieldTileWidth() - width)/2,(getBattleFieldTileHeight() - height)/2);

        obstacles.addActor(createObstacle(space, width, height));
    }

    protected Obstacle createObstacle(Vector2 pos, float width, float height) {
        return new Box(getBattleFieldBodyHandler(), pos, width, height);
    }

     private class Box extends Obstacle {

        private Box(BodyHandler bodyHandler, Vector2 pos, float width, float height) {
            super(BitCategories.WALL, pos, width, height);
            setSize(width, height);

            body = bodyHandler.requestCreation(this, pos.add(width/2, height/2), BodyDef.BodyType.DynamicBody);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(getWidth()/2, getHeight()/2);

            bodyHandler.createFixture(body, this, shape, this.getCategory(), BitCategories.ALL.bit(), false, 0.5f, 0.1f);

            body.setFixedRotation(false);

            body.getMassData().mass = 75;

            body.setTransform(body.getPosition(), (float) Math.toRadians(30f * (getRandom().nextInt(12))));

            body.setAngularDamping(6);
            body.setLinearDamping(6);
        }
    }
}
