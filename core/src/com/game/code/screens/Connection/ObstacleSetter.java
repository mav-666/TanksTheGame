package com.game.code.screens.Connection;

import com.game.code.BattleField.*;

import java.util.Collections;

public class ObstacleSetter implements BattleFieldContentSetter {
    private final BattleFieldBuilderDecorators type;
    private final PercentParameter density;
    private final ObstacleBuilderData decoratorJsonData;

    public ObstacleSetter(BattleFieldBuilderDecorators type) {
        decoratorJsonData = new ObstacleBuilderData();
        decoratorJsonData.type = type.name();
        this.type = type;
        density = new PercentParameter();
    }

    @Override
    public Iterable<ContentParameter<?>> getParameters() {
        return Collections.singleton(density);
    }

    @Override
    public BattleFieldBuilderDecorators getType() {
        return type;
    }

    @Override
    public BattleFieldBuilderDecoratorJsonData getData() {
        decoratorJsonData.density = density.getValue();
        return decoratorJsonData;
    }


}
