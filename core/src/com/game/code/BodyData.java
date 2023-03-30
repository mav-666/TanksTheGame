package com.game.code;

import com.game.code.Entity.Entity;

public class BodyData {
    public final BodyHandler bodyHandler;
    public Entity owner;
    public boolean flaggedForDispose;
    public final int index;

    BodyData(BodyHandler bodyHandler, Entity owner, int index) {
        this.bodyHandler = bodyHandler;
        this.owner = owner;
        this.index = index;
        flaggedForDispose = false;
    }
}
