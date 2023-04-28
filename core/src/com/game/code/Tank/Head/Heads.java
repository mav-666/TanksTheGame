package com.game.code.Tank.Head;

import com.game.code.utils.box2d.BodyHandler;
import com.game.code.Tank.Tank;

public enum Heads {
    Canon((bodyHandler, tank, data) -> new Canon(bodyHandler, tank, tank.getWidth(), tank.getHeight(), data));

    public Creator creator;

    Heads(Creator creator) {
        this.creator = creator;
    }

    public interface Creator {
        Head create(BodyHandler bodyHandler, Tank tank, HeadData headData);
    }
}
