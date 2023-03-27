package com.game.code.Tank;

import com.badlogic.gdx.physics.box2d.World;
import com.game.code.Tank.Head.Canon;
import com.game.code.Tank.Head.CanonData;
import com.game.code.Tank.Head.Head;
import com.game.code.Tank.Head.HeadData;

public enum Heads {
    Canon((world, tank, data) -> new Canon(world, tank, tank.getWidth(), tank.getHeight(), (CanonData) data));

    Creator creator;

    Heads(Creator creator) {
        this.creator = creator;
    }

    interface Creator {
        Head create(World world, Tank tank, HeadData headData);
    }
}
