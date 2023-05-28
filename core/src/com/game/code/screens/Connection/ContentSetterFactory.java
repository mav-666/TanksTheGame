package com.game.code.screens.Connection;

import com.game.code.BattleField.BattleFieldBuilderDecorators;

public class ContentSetterFactory {
    public BattleFieldContentSetter createContentSetter(BattleFieldBuilderDecorators type) {
        return switch (type) {
            case box, grass, gasoline -> new ObstacleSetter(type);
            default -> new BattleFieldContentSetterImpl(type);
        };
    }

    public BattleFieldContentSetter createContentSetter(ObstacleBuilders type) {
        return new ObstacleSetter(BattleFieldBuilderDecorators.valueOf(type.name()));
    }
}
