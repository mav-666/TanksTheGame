package com.game.code.Tank.Cab;

import com.badlogic.gdx.math.Vector2;
import com.game.code.utils.box2d.BodyHandler;
import com.game.code.Tank.Tank;

public enum Cabs {
    Cab((bodyHandler, tank, data) -> new Cab(bodyHandler, new Vector2(tank.getX(), tank.getY()), tank.getWidth(), tank.getHeight(), data));

    public Creator creator;

    Cabs(Creator creator) {
        this.creator = creator;
    }

    public interface Creator {
        Cab createCab(BodyHandler bodyHandler, Tank tank, CabData cabData);
    }
}
