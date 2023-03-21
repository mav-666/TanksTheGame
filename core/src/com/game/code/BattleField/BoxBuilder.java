package com.game.code.BattleField;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.Entity.BitCategories;
import com.game.code.TextureActor;

import java.util.List;

public class BoxBuilder extends ObstacleBuilder implements AssetRequest {

    public BoxBuilder(AssetRequestProcessor assetRequestProcessor,
                      float density, BattleFieldBuilder battleFieldBuilder) {
        super(density, battleFieldBuilder);
        request(assetRequestProcessor);
    }

    @Override
    public void request(AssetRequestProcessor assetRequestProcessor) {
        assetRequestProcessor.receiveRequest("TanksTheGame.atlas", TextureAtlas.class, this);
    }

    @Override
    public void passAssets(AssetRequestProcessor assets) {
        Array<TextureAtlas.AtlasRegion> tileTextures =
                assets.get("TanksTheGame.atlas", TextureAtlas.class).findRegions("box");

        obstacles.getChildren().forEach((actor) ->
                ((TextureActor) actor).setTexture((tileTextures.get((int)(Math.random() * tileTextures.size)))));
    }

    @Override
    protected void addObstacle() {
        List<Vector2> spaces = getFreeSpace().keySet().stream().toList();
        Vector2 space = spaces.get((int)(Math.random() * spaces.size()));

        float width = (float) Math.random() * (getBattleField().getTileWidth() - 0.5f) + 0.5f;
        float height = (float) Math.random() * (getBattleField().getTileHeight() - 0.5f) + 0.5f;

        obstacles.addActor(createBox(getFreeSpace().get(space), width, height));
        getFreeSpace().remove(space);
    }

    private TextureActor createBox(Vector2 pos, float width, float height) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        return new Obstacle(getBattleField().getWorld(), BodyDef.BodyType.DynamicBody, BitCategories.WALL, shape, pos, width, height);
    }
}
