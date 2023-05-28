package com.game.code.BattleField;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.game.code.AssetManagment.AssetProcessor;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.BattleFieldFineVersion.Obstacle;
import com.game.code.utils.scene2d.TexturedActor;

public class BoxBuilder extends ObstacleBuilder implements AssetRequest {

    public BoxBuilder(float density, BattleFieldBuilder battleFieldBuilder) {
        super(density, battleFieldBuilder);
    }

    @Override
    public void request(AssetProcessor assetRequestProcessor) {
        super.request(assetRequestProcessor);

        assetRequestProcessor.receiveRequest("TanksTheGame.atlas", TextureAtlas.class, this);
    }

    @Override
    public void passAssets(AssetProcessor assets) {
        Array<TextureAtlas.AtlasRegion> tileTextures =
                assets.get("TanksTheGame.atlas", TextureAtlas.class).findRegions("box");

        obstacles.getChildren().forEach((actor) ->
                ((TexturedActor) actor).setTexture((tileTextures.get(getRandom().nextInt(tileTextures.size)))));
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
        return null;
    }
}
