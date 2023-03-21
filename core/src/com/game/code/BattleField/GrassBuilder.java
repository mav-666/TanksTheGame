package com.game.code.BattleField;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.Entity.BitCategories;
import com.game.code.Entity.Entity;
import com.game.code.TextureActor;

import java.util.List;

public class GrassBuilder extends ObstacleBuilder implements AssetRequest {

    public GrassBuilder(AssetRequestProcessor assetRequestProcessor,
                 float density, BattleFieldBuilder battleFieldBuilder) {
        super(density, battleFieldBuilder);
        request(assetRequestProcessor);
    }

    @Override
    public void request(AssetRequestProcessor assetRequestProcessor) {
        assetRequestProcessor.receiveRequest("TanksTheGame.atlas", TextureAtlas.class, this);
    }

    @Override
    public void passAssets(AssetRequestProcessor assets) {
        Array<TextureAtlas.AtlasRegion> tileTextures =
                assets.get("TanksTheGame.atlas", TextureAtlas.class).findRegions("grass");

        obstacles.getChildren().forEach((actor) ->
                ((TextureActor) actor).setTexture((tileTextures.get((int)(Math.random() * tileTextures.size)))));
    }

    @Override
    protected void addObstacle() {
        List<Vector2> spaces = getFreeSpace().keySet().stream().toList();
        Vector2 space = spaces.get((int)(Math.random() * spaces.size()));

        obstacles.addActor(createGrass(getFreeSpace().get(space),
                getBattleField().getTileWidth(), getBattleField().getTileHeight()));

        getFreeSpace().remove(space);
    }

    private Obstacle createGrass(Vector2 pos , float width, float height) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2 - 0.05f, height/2 - 0.05f);

        Obstacle grass =  new Obstacle(getBattleField().getWorld(), BodyDef.BodyType.StaticBody, BitCategories.AREA, shape, pos, width, height)
        {
            @Override
            public void collusionRespond(Entity participant) {
                super.collusionRespond(participant);

                if(!(participant instanceof Actor))
                    return;

                Actor actor = (Actor) participant;

                actor.getColor().a /= 1.5;

            }

            @Override
            public void endContactRespond(Entity participant) {
                super.endContactRespond(participant);
                super.collusionRespond(participant);

                if(!(participant instanceof Actor))
                    return;

                Actor actor = (Actor) participant;

                actor.getColor().a *= 1.5;
            }
        };

        grass.getBody().getFixtureList().first().setSensor(true);

        return grass;
    }
}
