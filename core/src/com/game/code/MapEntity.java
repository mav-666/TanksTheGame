package com.game.code;

import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.Entity.Entity;

public class MapEntity implements Entity {


    @Override
    public void collusionRespond(Entity participant) {
        Entity.super.collusionRespond(participant);

    }

    @Override
    public Body getBody() {
        return null;
    }

    @Override
    public short getCategory() {
        return 0;
    }
}
