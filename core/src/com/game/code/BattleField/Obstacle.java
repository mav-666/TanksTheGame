package com.game.code.BattleField;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.code.BodyBuilder;
import com.game.code.Entity.BitCategories;
import com.game.code.Entity.Entity;
import com.game.code.Entity.Wall;
import com.game.code.TextureActor;

public class Obstacle extends TextureActor implements Entity {
    private Body body;

    private BitCategories category;

    Obstacle(World world, BodyDef.BodyType type, BitCategories category, Shape bodyShape, Vector2 pos, float width, float height){
        this.category = category;

        setSize(width, height);
        setPosition(pos.x, pos.y);
        setOrigin(this.getWidth()/2, this.getHeight()/2);

        body = BodyBuilder.createBody(world, this, pos.add(getWidth()/2, getHeight()/2), bodyShape, type, this.getCategory(), BitCategories.ALL.bit(),0.5f, 0,75);

        body.setFixedRotation(false);
        body.setAngularDamping(6);
        body.setLinearDamping(6);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        setRotation((float) Math.toDegrees(body.getAngle()));
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public short getCategory() {
        return this.category.bit();
    }
}
