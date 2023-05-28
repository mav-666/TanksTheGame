package com.game.code.Tank;

import com.badlogic.gdx.math.Vector2;
import com.game.code.utils.box2d.BodyHandler;
import com.game.code.Tank.Cab.Cab;
import com.game.code.Tank.Head.Head;

public class TankFactory {

    public Tank createTank(BodyHandler bodyHandler, Vector2 pos, float width, float height, HeadData headData, CabData cabData) {
        Tank tank = new TankImpl(pos, width, height);

        tank.setCab(createCab(bodyHandler, tank, cabData));
        tank.setHead(createHead(bodyHandler, tank, headData));

        return tank;
    }

    private Head createHead(BodyHandler bodyHandler, Tank tank, HeadData headData) {
        return Heads.valueOf(headData.type).creator.create(bodyHandler, tank, headData);
    }

    private Cab createCab(BodyHandler bodyHandler, Tank tank, CabData cabData) {
        return Cabs.valueOf(cabData.type).creator.createCab(bodyHandler, tank, cabData);
    }
}
