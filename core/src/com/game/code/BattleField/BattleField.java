package com.game.code.BattleField;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.BodyBuilder;
import com.game.code.Entity.BitCategories;
import com.game.code.Entity.Wall;

import java.util.HashMap;
import java.util.HashSet;

public class BattleField extends Group implements Wall {
    private final Body body;

    public BattleField(World world, float width, float height) {
        setSize(width, height);
        ChainShape shape= new ChainShape();
        shape.createChain(new float[] {0,0, width-2,0, width-2,height-2, 0,height-2, 0,0});

        body = BodyBuilder.createBody(world, this, new Vector2(1,1), BodyDef.BodyType.StaticBody, 1);
        BodyBuilder.createFixture(body, this, shape, this.getCategory(), BitCategories.ALL.bit(),0.1f, 0);

    }

    @Override
    public Body getBody() {
        return body;
    }
}
