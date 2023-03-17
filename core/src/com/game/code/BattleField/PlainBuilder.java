package com.game.code.BattleField;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.TextureActor;

import java.util.HashMap;
import java.util.HashSet;

public class PlainBuilder extends BattleFieldBuilder implements AssetRequest {
    private final String plainTileName;
    private Group plain;
    private final float tileWidth, tileHeight;

    public PlainBuilder(World world, float width, float height, String plainTileName, float tileWidth, float tileHeight) {
        super(world, width, height);
        this.plainTileName = plainTileName;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    @Override
    public void buildBattleField() {
        super.buildBattleField();

        buildPlain();
    }

    void buildPlain() {
        plain = new Group();

        TextureActor plainTile;
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                plainTile = new TextureActor();
                plainTile.setSize(tileWidth, tileHeight);
                plainTile.setPosition(i * tileWidth, j * tileHeight);

                plain.addActor(plainTile);
            }
        }

        battleField.addActor(plain);
    }

    @Override
    public void request(HashMap<String, Class<?>> requests, HashSet<AssetRequest> clients) {
        addRequest(requests, clients, "TanksTheGame.atlas", TextureAtlas.class, this);
    }

    @Override
    public void passAssets(AssetRequestProcessor assets) {
        Array<TextureAtlas.AtlasRegion> plainTextures = assets.get("TanksTheGame.atlas", TextureAtlas.class).findRegions(plainTileName);

        plain.getChildren().forEach((actor) ->
            ((TextureActor) actor).setTexture((plainTextures.get((int)(Math.random() * plainTextures.size)))));
    }
}
