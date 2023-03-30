package com.game.code.BattleField;

import com.badlogic.gdx.math.Vector2;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.BodyHandler;

import java.util.HashSet;
import java.util.Random;

public class BattleFiledBuilderDecorator implements BattleFieldBuilder, AssetRequest {
    BattleFieldBuilder battleFieldBuilder;

    BattleFiledBuilderDecorator(BattleFieldBuilder battleFieldBuilder) {
        this.battleFieldBuilder = battleFieldBuilder;
    }

    @Override
    public void buildBattleField() {
        battleFieldBuilder.buildBattleField();
    }

    @Override
    public BattleField createBattleField() {
        return battleFieldBuilder.createBattleField();
    }

    @Override
    public void request(AssetRequestProcessor assetRequestProcessor) {
        if(battleFieldBuilder instanceof AssetRequest) ((AssetRequest) battleFieldBuilder).request(assetRequestProcessor);
    }

    @Override
    public void passAssets(AssetRequestProcessor assets) {}

    @Override
    public HashSet<Vector2> getFreeSpace() {
        return battleFieldBuilder.getFreeSpace();
    }

    @Override
    public Random getRandom() {
        return battleFieldBuilder.getRandom();
    }

    @Override
    public float getBattleFieldWidth() {
        return battleFieldBuilder.getBattleFieldWidth();
    }

    @Override
    public float getBattleFieldHeight() {
        return battleFieldBuilder.getBattleFieldHeight();
    }

    @Override
    public float getBattleFieldTileWidth() {
        return battleFieldBuilder.getBattleFieldTileWidth();
    }

    @Override
    public float getBattleFieldTileHeight() {
        return battleFieldBuilder.getBattleFieldTileHeight();
    }

    @Override
    public BodyHandler getBattleFieldBodyHandler() {
        return battleFieldBuilder.getBattleFieldBodyHandler();
    }
}
