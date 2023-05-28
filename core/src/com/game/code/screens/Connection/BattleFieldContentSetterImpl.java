package com.game.code.screens.Connection;

import com.game.code.BattleField.BattleFieldBuilderDecoratorJsonData;
import com.game.code.BattleField.BattleFieldBuilderDecorators;
import com.game.code.BattleField.ContentParameter;

import java.util.Collections;

public class BattleFieldContentSetterImpl implements BattleFieldContentSetter {
    private final BattleFieldBuilderDecoratorJsonData decoratorJsonData;
    private final BattleFieldBuilderDecorators type;

    public BattleFieldContentSetterImpl(BattleFieldBuilderDecorators type) {
        this.type = type;
        decoratorJsonData = new BattleFieldBuilderDecoratorJsonData();
        decoratorJsonData.type = type.name();
    }

    @Override
    public Iterable<ContentParameter<?>> getParameters() {
        return Collections.EMPTY_SET;
    }

    @Override
    public BattleFieldBuilderDecorators getType() {
        return type;
    }

    @Override
    public BattleFieldBuilderDecoratorJsonData getData() {
        return decoratorJsonData;
    }
}
