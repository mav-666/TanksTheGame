package com.game.code.BattleField;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.game.code.AssetManagment.AssetProcessor;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.BattleFieldFineVersion.Obstacle;
import com.game.code.utils.scene2d.TexturedActor;

public class GrassBuilder extends ObstacleBuilder implements AssetRequest {
    Obstacle grassGroup;

    public GrassBuilder(float density, BattleFieldBuilder battleFieldBuilder) {
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
                assets.get("TanksTheGame.atlas", TextureAtlas.class).findRegions("grass");

        obstacles.getChildren().forEach((actor) ->
                ((TexturedActor) actor).setTexture((tileTextures.get(getRandom().nextInt(tileTextures.size)))));
    }

    @Override
    protected void buildObstacles() {
        Vector2 pos = Vector2.Zero;
        grassGroup =  createObstacle(pos, getBattleFieldWidth(), getBattleFieldHeight());
        grassGroup.setBody(getBattleFieldBodyHandler().requestCreation(grassGroup, pos, BodyDef.BodyType.StaticBody));

        super.buildObstacles();
    }

    @Override
    protected void addObstacle() {
        Vector2 space = getFreeSpace().stream().toList().get(getRandom().nextInt(getFreeSpace().size()));
        getFreeSpace().remove(space);

        obstacles.addActor(createGrassPiece(space, getBattleFieldTileWidth(), getBattleFieldTileHeight()));
    }

    protected Obstacle createObstacle(Vector2 pos, float width, float height) {

    }

    private Obstacle createGrassPiece(Vector2 pos, float width, float height) {

    }
}
