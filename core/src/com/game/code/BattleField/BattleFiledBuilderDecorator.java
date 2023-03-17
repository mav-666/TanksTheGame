package com.game.code.BattleField;

public abstract class BattleFiledBuilderDecorator extends BattleFieldBuilder {
    BattleFieldBuilder battleFieldBuilder;

    BattleFiledBuilderDecorator(BattleFieldBuilder battleFieldBuilder) {
        super(battleFieldBuilder.world, battleFieldBuilder.width, battleFieldBuilder.height);
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
}
