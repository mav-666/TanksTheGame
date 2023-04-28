package com.game.code.Entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.utils.box2d.BodyHandler;

public interface DelayedEntity extends Entity {
    void initBody(Body body, BodyHandler bodyHandler);
}
