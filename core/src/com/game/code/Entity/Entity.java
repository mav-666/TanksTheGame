package com.game.code.Entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.BodyData;

public interface Entity {

    default void collusionRespond(Entity participant) {}

    default void endContactRespond(Entity participant) {}

    default void destroy() {
        getBody().getWorld().destroyBody(getBody());
    }

    default void flagForDispose() {
        if(getBody().getUserData() != null)
            ((BodyData) getBody().getUserData()).flaggedForDispose = true;
    }

    Body getBody();

    short getCategory();

}
