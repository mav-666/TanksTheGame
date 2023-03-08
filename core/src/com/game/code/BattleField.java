package com.game.code;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.code.Entity.BitCategories;
import com.game.code.Entity.Wall;

public class BattleField extends TextureEntity implements Wall {
    private Body body;


    public BattleField(World world, TextureRegion textureRegion, float width, float height) {
        super(textureRegion);
        setSize(width, height);

        ChainShape shape= new ChainShape();
        shape.createChain(new float[] {0,0, width,0, width,height, 0,height, 0,0});

        body = BodyBuilder.createBody(world, this, new Vector2(0,0), shape, BodyDef.BodyType.StaticBody, this.getCategory(), BitCategories.ALL.bit(),0.1f, 0);
    }

    @Override
    public Body getBody() {
        return body;
    }
}
