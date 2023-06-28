package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class HasFriendsComponent implements Component, Pool.Poolable {
    public short friendlyGroup;

    @Override
    public void reset() {
        friendlyGroup = 0;
    }
}
