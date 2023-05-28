package com.game.code.screens.Connection;

import com.game.code.BattleField.BattleFieldBuilderJsonData;
import com.game.code.BattleField.PlainBuilderData;

public class InternalConnectionFactory implements ConnectionFactory {
    BattleFieldSetter setter;

    public InternalConnectionFactory() {
        setter = new InternalBattleFieldSetter();
    }

    @Override
    public BattleFieldBuilderJsonData getSettings() {
        return setter.getSettings();
    }

    @Override
    public BattleFieldSetter getBattleFieldSetter() {
        return setter;
    }

    @Override
    public Iterable<User> getUsers() {
        return null;
    }

    @Override
    public void addUser() {

    }
}
