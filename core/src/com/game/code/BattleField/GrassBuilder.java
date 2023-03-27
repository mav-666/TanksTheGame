package com.game.code.BattleField;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.BodyBuilder;
import com.game.code.Entity.BitCategories;
import com.game.code.Entity.Entity;
import com.game.code.Player;
import com.game.code.TextureActor;

import java.util.HashMap;

public class GrassBuilder extends ObstacleBuilder implements AssetRequest {
    Obstacle grassGroup;

    public GrassBuilder(float density, BattleFieldBuilder battleFieldBuilder) {
        super(density, battleFieldBuilder);
    }

    @Override
    public void request(AssetRequestProcessor assetRequestProcessor) {
        super.request(assetRequestProcessor);

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
    protected void buildObstacles() {
        Vector2 pos = new Vector2(0,0);
        grassGroup =  createObstacle(pos, getBattleFieldWidth(), getBattleFieldHeight());
        grassGroup.setBody(BodyBuilder.createBody(getBattleFieldWorld(), grassGroup, pos, BodyDef.BodyType.StaticBody, 1));

        super.buildObstacles();
    }

    @Override
    protected void addObstacle() {
        Vector2 space = getFreeSpace().stream().toList().get(getRandom().nextInt(getFreeSpace().size()));
        getFreeSpace().remove(space);

        obstacles.addActor(createGrassPiece(space, getBattleFieldTileWidth(), getBattleFieldTileHeight()));
    }

    protected Obstacle createObstacle(Vector2 pos, float width, float height) {
        return new Obstacle(BitCategories.AREA,
                pos, width, height)
        {
            private final HashMap<Actor, Integer> faded = new HashMap<>();

            @Override
            public void collusionRespond(Entity participant) {
                super.collusionRespond(participant);

                if(!(participant instanceof Actor))
                    return;

                Actor actor = (Actor) participant;

                if(faded.containsKey(actor)){
                    faded.put(actor, faded.get(actor) + 1);
                    return;
                }
                else
                    faded.put(actor, 0);

                Color fadedColor = new Color(actor.getColor());

                if(participant instanceof Player)
                    fadedColor.a = 0.5f;
                else
                    fadedColor.a = 0f;
                actor.addAction(Actions.color(fadedColor, 0.3f));
            }

            @Override
            public void endContactRespond(Entity participant) {
                super.endContactRespond(participant);
                super.collusionRespond(participant);

                if(!(participant instanceof Actor))
                    return;

                Actor actor = (Actor) participant;

                if(faded.get(actor) != 0) {
                    faded.put(actor, faded.get(actor) - 1);
                    return;
                }

                faded.remove(actor);

                Color fadedColor = new Color(actor.getColor());
                fadedColor.a = 1f;

                actor.addAction(Actions.color(fadedColor, 0.3f));
            }
        };
    }

    private Obstacle createGrassPiece(Vector2 pos, float width, float height) {
        Obstacle grass = new Obstacle(BitCategories.AREA, pos, width, height);
        grass.setScale(1.25f);

        float shapeW = width/8, shapeH = height/8;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(shapeW, shapeH, pos.add(width/2, height/2), 0);


        Fixture fixture = BodyBuilder.createFixture(grassGroup.getBody(), grassGroup, shape, BitCategories.AREA.bit(), BitCategories.ALL.bit(), 0.1f, 0);
        fixture.setSensor(true);

        return grass;
    }
}
