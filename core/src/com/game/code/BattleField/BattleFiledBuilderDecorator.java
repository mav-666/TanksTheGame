package com.game.code.BattleField;

import com.badlogic.gdx.math.Vector2;
import java.util.HashMap;

public class BattleFiledBuilderDecorator implements BattleFieldBuilder {
    BattleFieldBuilder battleFieldBuilder;

    BattleFiledBuilderDecorator(BattleFieldBuilder battleFieldBuilder) {
        this.battleFieldBuilder = battleFieldBuilder;
    }

    @Override
    public void buildBattleField() {
        battleFieldBuilder.buildBattleField();
    }

    @Override
    public BattleField getBattleField() {
        return battleFieldBuilder.getBattleField();
    }

    @Override
    public HashMap<Vector2, Vector2> getFreeSpace() {
        return battleFieldBuilder.getFreeSpace();
    }
}
