package com.game.code.Tank;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.game.code.BodyHandler;

public enum Cabs {
    Cab((bodyHandler, tank, data) -> new Cab(bodyHandler, new Vector2(tank.getX(), tank.getY()), tank.getWidth(), tank.getHeight(), data));

    Creator creator;

    Cabs(Creator creator) {
        this.creator = creator;
    }

    interface Creator {
        Cab createCab(BodyHandler bodyHandler, Tank tank, CabData cabData);
    }
}
