package com.game.code.BattleField;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.game.code.BodyHandler;
import com.game.code.Entity.BitCategories;
import com.game.code.Entity.Wall;

public class BattleField extends Group implements Wall {
    private Body body;

    public BattleField(BodyHandler bodyHandler, float width, float height) {
        setSize(width, height);

        body = bodyHandler.requestCreation(this, new Vector2(1,1), BodyDef.BodyType.StaticBody);

        ChainShape shape= new ChainShape();
        shape.createChain(new float[] {0,0, getWidth()-2,0, getWidth()-2,getHeight()-2, 0,getHeight()-2, 0,0});

        bodyHandler.createFixture(body, this, shape, this.getCategory(), BitCategories.ALL.bit(), false, 0.1f, 0);
    }

    @Override
    public Body getBody() {
        return body;
    }
}
