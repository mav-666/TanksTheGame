package com.game.code.BattleField;

import com.game.code.utils.box2d.BodyHandler;

public class BattleFieldBuilderFromJsonData {

    public BattleFieldBuilder createBattleFieldBuilder(BodyHandler bodyHandler, BattleFieldBuilderJsonData jsonData) {
        BattleFieldBuilder battleFieldBuilder = BattleFieldBuilders.valueOf(jsonData.type).creator.create(bodyHandler, jsonData);

        for(var decorator : jsonData.decorators) {
            battleFieldBuilder = applyDecorator(battleFieldBuilder, decorator);
        }

        return battleFieldBuilder;
    }

    public BattleFieldBuilder applyDecorator(BattleFieldBuilder battleFieldBuilder, BattleFieldBuilderDecoratorJsonData jsonData) {
        return BattleFieldBuilderDecorators.valueOf(jsonData.type).creator.create(jsonData, battleFieldBuilder);
    }

}
