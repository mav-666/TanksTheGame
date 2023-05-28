package com.game.code.screens.Connection;

import com.game.code.BattleField.BattleFieldBuilderJsonData;

public interface ConnectionFactory {
    BattleFieldBuilderJsonData getSettings();

    BattleFieldSetter getBattleFieldSetter();

    Iterable<User> getUsers();

    void addUser();
}