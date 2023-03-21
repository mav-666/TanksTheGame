package com.game.code.BattleField;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.TextureActor;

public class BorderBuilder extends BattleFiledBuilderDecorator implements AssetRequest {
    private Group border;

    public BorderBuilder(AssetRequestProcessor assetRequestProcessor, BattleFieldBuilder battleFieldBuilder) {
        super(battleFieldBuilder);
        border = new Group();
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

        border.getChildren().forEach((actor) ->
                ((TextureActor) actor).setTexture((tileTextures.get((int)(Math.random() * tileTextures.size)))));
    }

    @Override
    public void buildBattleField() {
        super.buildBattleField();

        buildBorder();
    }

    protected void buildBorder() {
        getFreeSpace().values().stream().filter(
                (cord) -> cord.x == 0 || cord.y == 0 || cord.x == getBattleField().getWidth()-1 || cord.y == getBattleField().getHeight()-1
        ).toList().forEach( (vector2) -> {
            TextureActor actor = new TextureActor();
            actor.setPosition(vector2.x, vector2.y);
            actor.setSize(getBattleField().getTileWidth(), getBattleField().getTileHeight());
            border.addActor(actor);
        });

        getBattleField().addActor(border);
    }


}
