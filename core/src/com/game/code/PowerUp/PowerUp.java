package com.game.code.PowerUp;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;
import com.game.code.Entity.Area;
import com.game.code.Entity.Entity;
import com.game.code.utils.scene2d.TextureActor;

import java.util.Vector;

public class PowerUp extends TextureActor implements Area, Pool.Poolable {
    private Body body;

    PowerUp(ValuePowerUps type) {

    }

    public void init(Vector pos) {

    }

    @Override
    public void collusionRespond(Entity participant) {

    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public void reset() {

    }
}
