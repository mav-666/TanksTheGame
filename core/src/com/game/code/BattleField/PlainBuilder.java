package com.game.code.BattleField;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.TextureActor;

import java.util.HashSet;
import java.util.Random;

public class PlainBuilder implements BattleFieldBuilder, AssetRequest {
    private final Random random;
    private final World world;
    private final float width, height, tileWidth, tileHeight;

    private final HashSet<Vector2> freeSpace;

    private final String plainTileName;
    private Group plain;

    public PlainBuilder(int seed, World world, float width, float height,  float tileWidth, float tileHeight, String plainTileName) {
        random = new Random(seed);

        this.world = world;
        this.width = width;
        this.height = height;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        this.plainTileName = plainTileName;


        freeSpace = new HashSet<>();

    }

    @Override
    public void request(AssetRequestProcessor assetRequestProcessor) {
        assetRequestProcessor.receiveRequest("TanksTheGame.atlas", TextureAtlas.class, this);
    }

    @Override
    public void passAssets(AssetRequestProcessor assets) {
        Array<TextureAtlas.AtlasRegion> tileTextures = assets.get("TanksTheGame.atlas", TextureAtlas.class).findRegions(plainTileName);

        plain.getChildren().forEach((actor) ->
                ((TextureActor) actor).setTexture((tileTextures.get((getRandom().nextInt(tileTextures.size))))));
    }

    @Override
    public void buildBattleField() {
        BattleFieldBuilder.super.buildBattleField();
        buildPlain();
    }

    private void buildPlain() {
        plain = new Group();

        TextureActor plainTile;
        for(int i = 0; i < getBattleFieldWidth(); i++) {
            for(int j = 0; j < getBattleFieldHeight(); j++) {
                plainTile = new TextureActor();
                plainTile.setSize(getBattleFieldTileWidth(), getBattleFieldTileHeight());
                plainTile.setPosition(i * getBattleFieldTileWidth(), j * getBattleFieldTileHeight());
                plainTile.setScale(1.0001f);

                plain.addActor(plainTile);
            }
        }
    }

    @Override
    public BattleField createBattleField() {
        BattleField battleField = new BattleField(world, width, height);
        battleField.addActor(plain);

        return battleField;
    }

    @Override
    public HashSet<Vector2> getFreeSpace() {
        return freeSpace;
    }

    @Override
    public Random getRandom() {
        return random;
    }


    @Override
    public float getBattleFieldWidth() {
        return width;
    }

    @Override
    public float getBattleFieldHeight() {
        return height;
    }

    @Override
    public float getBattleFieldTileWidth() {
        return tileWidth;
    }

    @Override
    public float getBattleFieldTileHeight() {
        return tileHeight;
    }

    @Override
    public World getBattleFieldWorld() {
        return world;
    }


}
