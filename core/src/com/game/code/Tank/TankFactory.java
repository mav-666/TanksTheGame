package com.game.code.Tank;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.game.code.Tank.Head.Head;
import com.game.code.Tank.Head.HeadData;

public class TankFactory {

    public Tank createTank(World world, Vector2 pos, float width, float height, HeadData headData, CabData cabData) {
        Tank tank = new Tank(pos, width, height);

        tank.setCab(createCab(world, tank, cabData));
        tank.setHead(createHead(world, tank, headData));

        return tank;
    }

    private Head createHead(World world, Tank tank, HeadData headData) {
        return Heads.valueOf(headData.type).creator.create(world, tank, headData);
    }

    private Cab createCab(World world, Tank tank, CabData cabData) {
        return Cabs.valueOf(cabData.type).creator.createCab(world, tank, cabData);
    }
}
