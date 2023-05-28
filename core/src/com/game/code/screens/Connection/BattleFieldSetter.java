package com.game.code.screens.Connection;

import com.game.code.BattleField.BattleFieldBuilderJsonData;
import com.game.code.BattleField.BattleFieldBuilders;

public interface BattleFieldSetter {

    void setType(BattleFieldBuilders type);

    void setSeed(int seed);

    void setSize(int width, int height);

    void addContent(BattleFieldContentSetter contentSetter);

    BattleFieldBuilderJsonData getSettings();
}
