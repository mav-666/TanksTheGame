package com.game.code.BattleField;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.game.code.AssetManagment.AssetProcessor;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.utils.box2d.BodyHandler;
import com.game.code.utils.scene2d.TextureActor;

import java.util.HashSet;
import java.util.Random;

public class PlainBuilder implements BattleFieldBuilder, AssetRequest {
    private final Random random;
    private final BodyHandler bodyHandler;
    private final float width, height, tileWidth, tileHeight;

    private final HashSet<Vector2> freeSpace;

    private final String plainTileName;
    private Group plain;

    public PlainBuilder(BodyHandler bodyHandler, PlainBuilderData plainData) {
        random = new Random(plainData.seed);

        this.bodyHandler = bodyHandler;
        this.width = plainData.width;
        this.height = plainData.height;
        this.tileWidth = plainData.tileWidth;
        this.tileHeight = plainData.tileHeight;

        this.plainTileName = plainData.plainTileName;


        freeSpace = new HashSet<>();

    }

    @Override
    public void request(AssetProcessor assetRequestProcessor) {
        assetRequestProcessor.receiveRequest("TanksTheGame.atlas", TextureAtlas.class, this);
    }

    @Override
    public void passAssets(AssetProcessor assets) {
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
        BattleField battleField = new BattleField(bodyHandler, width, height);
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
    public BodyHandler getBattleFieldBodyHandler() {
        return bodyHandler;
    }


}
