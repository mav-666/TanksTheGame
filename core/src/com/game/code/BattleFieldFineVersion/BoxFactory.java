package com.game.code.BattleFieldFineVersion;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.game.code.Entity.BitCategories;
import com.game.code.utils.box2d.BodyHandler;

public class BoxFactory implements ObstacleFactory {
    @Override
    public Obstacle create(BodyHandler bodyHandler, Vector2 pos, float width, float height) {
        return new Box(bodyHandler, pos, width, height);
    }

    private class Box extends Obstacle {

        private Box(BodyHandler bodyHandler, Vector2 pos, float width, float height) {
            super(BitCategories.WALL, pos, width, height);
            setSize(width, height);

            body = bodyHandler.requestCreation(this, pos.add(width/2, height/2), BodyDef.BodyType.DynamicBody);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(getWidth()/2, getHeight()/2);

            bodyHandler.createFixture(body, this, shape, this.getCategory(), BitCategories.ALL.bit(), false, 0.5f, 0.1f);

            body.setFixedRotation(false);

            body.getMassData().mass = 75;

            body.setTransform(body.getPosition(), (float) Math.toRadians(30f * (Math.random()*12)));

            body.setAngularDamping(6);
            body.setLinearDamping(6);
        }
    }
}
