package com.game.code.screens.Connection;

import com.game.code.BattleField.BattleFieldBuilderDecoratorJsonData;
import com.game.code.BattleField.BattleFieldBuilderDecorators;
import com.game.code.BattleField.ContentParameter;

public interface BattleFieldContentSetter {

    Iterable<ContentParameter<?>> getParameters();

    BattleFieldBuilderDecorators getType();

    BattleFieldBuilderDecoratorJsonData getData();
}
