package com.game.code.BattleFieldFineVersion;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.Entity.BitCategories;
import com.game.code.Entity.Entity;
import com.game.code.utils.scene2d.TexturedActor;

public class Obstacle extends TexturedActor implements Entity {
    protected Body body;

    private final BitCategories category;

    Obstacle(BitCategories category, Vector2 pos, float width, float height){
        this.category = category;

        setSize(width, height);
        setPosition(pos.x, pos.y);
        setOrigin(getWidth()/2, getHeight()/2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(body == null) return;

        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRotation((float) Math.toDegrees(body.getAngle()));

    }

    @Override
    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public short getCategory() {
        return this.category.bit();
    }
}
