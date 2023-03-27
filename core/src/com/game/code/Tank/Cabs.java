package com.game.code.Tank;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public enum Cabs {
    Cab((world, tank, data) -> new Cab(world, new Vector2(tank.getX(), tank.getY()), tank.getWidth(), tank.getHeight(), data));

    Creator creator;

    Cabs(Creator creator) {
        this.creator = creator;
    }

    interface Creator {
        Cab createCab(World world, Tank tank, CabData cabData);
    }
}
