package com.game.code.utils.scene2d;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class BodyFollowingActor extends TexturedActor {
    private Body target;

    BodyFollowingActor(TextureRegion texture) {
        super(texture);
    }

    public void follow(Body body) {
        this.target = body;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        setPosition(target.getPosition().x - getWidth() / 2,
                target.getPosition().y - getHeight() / 2);
        setRotation((float) Math.toDegrees(target.getAngle()));
    }
}
