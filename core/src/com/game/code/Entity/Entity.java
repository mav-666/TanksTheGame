package com.game.code.Entity;

import com.badlogic.gdx.physics.box2d.Body;

public interface Entity {

    default void collusionRespond(Entity participant) {}

    default void endContactRespond(Entity participant) {}

    default void destroy() {
        getBody().setActive(false);
        getBody().getWorld().destroyBody(getBody());
    }


    Body getBody();

    short getCategory();

}
