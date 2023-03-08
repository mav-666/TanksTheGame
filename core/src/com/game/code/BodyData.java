package com.game.code;

import com.game.code.Entity.Entity;

public class BodyData {
    public final Entity owner;
    public final long index;
    public boolean flaggedForDispose;

    BodyData(Entity owner, short index) {
        this.owner = owner;
        this.index = index;
        this.flaggedForDispose = false;
    }
}
