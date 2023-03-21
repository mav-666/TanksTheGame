package com.game.code.BattleField;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.TextureActor;

import java.util.HashMap;

public class PlainBuilder implements BattleFieldBuilder, AssetRequest {
    private final BattleField battleField;

    private final HashMap<Vector2, Vector2> freeSpace;

    private final String plainTileName;
    private Group plain;

    public PlainBuilder(World world, AssetRequestProcessor assetRequestProcessor, float width, float height, String plainTileName, float tileWidth, float tileHeight) {
        this.plainTileName = plainTileName;

        battleField = new BattleField(world, width, height, tileWidth, tileHeight);

        freeSpace = new HashMap<>();
        this.resetMap();

        request(assetRequestProcessor);
    }

    @Override
    public void request(AssetRequestProcessor assetRequestProcessor) {
        assetRequestProcessor.receiveRequest("TanksTheGame.atlas", TextureAtlas.class, this);
    }

    @Override
    public void passAssets(AssetRequestProcessor assets) {
        Array<TextureAtlas.AtlasRegion> tileTextures = assets.get("TanksTheGame.atlas", TextureAtlas.class).findRegions(plainTileName);

        plain.getChildren().forEach((actor) ->
                ((TextureActor) actor).setTexture((tileTextures.get((int)(Math.random() * tileTextures.size)))));
    }

    @Override
    public void buildBattleField() {
        buildPlain();
    }

    private void buildPlain() {
        plain = new Group();

        TextureActor plainTile;
        for(int i = 0; i < battleField.getWidth(); i++) {
            for(int j = 0; j < battleField.getHeight(); j++) {
                plainTile = new TextureActor();
                plainTile.setSize(battleField.getTileWidth(), battleField.getTileHeight());
                plainTile.setPosition(i * battleField.getTileWidth(), j * battleField.getTileHeight());
                plainTile.setScale(1.0001f);

                plain.addActor(plainTile);
            }
        }

        battleField.addActor(plain);
    }

    @Override
    public BattleField getBattleField() {
        return battleField;
    }

    @Override
    public HashMap<Vector2, Vector2> getFreeSpace() {
        return freeSpace;
    }
}
