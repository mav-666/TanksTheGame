package com.game.code.BattleField;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.TextureActor;

public class BorderBuilder extends BattleFiledBuilderDecorator implements AssetRequest {
    private Group border;

    public BorderBuilder(BattleFieldBuilder battleFieldBuilder) {
        super(battleFieldBuilder);
        border = new Group();
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

        border.getChildren().forEach((actor) ->
                ((TextureActor) actor).setTexture(tileTextures.get(getRandom().nextInt(tileTextures.size))));
    }

    @Override
    public void buildBattleField() {
        super.buildBattleField();

        buildBorder();
    }

    protected void buildBorder() {
        getFreeSpace().stream().filter(
                (cord) -> cord.x == 0 || cord.y == 0 || cord.x == getBattleFieldWidth()-1 || cord.y == getBattleFieldHeight()-1
        ).toList().forEach( (vector2) -> {
            getFreeSpace().remove(vector2);
            TextureActor actor = new TextureActor();
            actor.setPosition(vector2.x, vector2.y);
            actor.setSize(getBattleFieldTileWidth(), getBattleFieldTileHeight());
            border.addActor(actor);
        });
    }

    @Override
    public BattleField createBattleField() {
        BattleField battleField = super.createBattleField();
        battleField.addActor(border);

        return battleField;
    }

}
