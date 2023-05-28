package com.game.code.BattleField;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.game.code.AssetManagment.*;
import com.game.code.BattleFieldFineVersion.Obstacle;
import com.game.code.utils.scene2d.TexturedActor;

public class GasolineBuilder extends ObstacleBuilder implements AssetRequest {

    public GasolineBuilder(float density, BattleFieldBuilder battleFieldBuilder) {
        super(density, battleFieldBuilder);
    }

    @Override
    public void request(AssetProcessor assetRequestProcessor) {
        super.request(assetRequestProcessor);

        assetRequestProcessor.receiveRequest("TanksTheGame.atlas", TextureAtlas.class, this);

        obstacles.getChildren().forEach(((actor -> ((AssetRequest) actor).request(assetRequestProcessor))));
    }

    @Override
    public void passAssets(AssetProcessor assets) {
        super.passAssets(assets);

        Array<TextureAtlas.AtlasRegion> tileTextures = assets.get("TanksTheGame.atlas", TextureAtlas.class).findRegions("gasoline");

        obstacles.getChildren().forEach((actor) ->
                ((TexturedActor) actor).setTexture((tileTextures.get(getRandom().nextInt(tileTextures.size)))));

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

}
