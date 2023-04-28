package com.game.code.BattleField;

public enum BattleFieldBuilderDecorators {
    grass((data, battleFieldBuilder) -> new GrassBuilder((ObstacleBuilderData) data, battleFieldBuilder)),
    gasoline((data, battleFieldBuilder) -> new GasolineBuilder((ObstacleBuilderData) data, battleFieldBuilder)),
    box((data, battleFieldBuilder) -> new BoxBuilder((ObstacleBuilderData) data, battleFieldBuilder)),
    border((data, battleFieldBuilder) -> new BorderBuilder(battleFieldBuilder)),
    tank((data, battleFieldBuilder) -> new TankBuilder((TankBuilderData) data, battleFieldBuilder)),
    ;

    public Creator creator;

    BattleFieldBuilderDecorators(Creator creator) {
        this.creator = creator;
    }

    public interface Creator {
        BattleFieldBuilderDecorator create(BattleFieldBuilderDecoratorJsonData builderDecoratorData,
                                           BattleFieldBuilder battleFieldBuilder);
    }
}
