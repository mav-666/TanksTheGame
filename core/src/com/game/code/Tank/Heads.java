package com.game.code.Tank;

import com.game.code.BodyHandler;
import com.game.code.Tank.Head.Canon;
import com.game.code.Tank.Head.CanonData;
import com.game.code.Tank.Head.Head;
import com.game.code.Tank.Head.HeadData;

public enum Heads {
    Canon((bodyHandler, tank, data) -> new Canon(bodyHandler, tank, tank.getWidth(), tank.getHeight(), (CanonData) data));

    Creator creator;

    Heads(Creator creator) {
        this.creator = creator;
    }

    interface Creator {
        Head create(BodyHandler bodyHandler, Tank tank, HeadData headData);
    }
}
