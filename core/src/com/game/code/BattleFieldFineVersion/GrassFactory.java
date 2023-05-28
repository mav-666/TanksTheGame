//package com.game.code.BattleFieldFineVersion;
//
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.Body;
//import com.badlogic.gdx.physics.box2d.BodyDef;
//import com.badlogic.gdx.physics.box2d.PolygonShape;
//import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.scenes.scene2d.actions.Actions;
//import com.game.code.AssetManagment.AssetProcessor;
//import com.game.code.AssetManagment.AssetRequest;
//import com.game.code.Entity.Area;
//import com.game.code.Entity.BitCategories;
//import com.game.code.Entity.Entity;
//import com.game.code.Tank.PlayableTank;
//import com.game.code.utils.box2d.BodyHandler;
//
//import java.util.HashMap;
//
//public class GrassFactory implements ObstacleFactory, AssetRequest {
//    Area grassGroup;
//
//    @Override
//    public void request(AssetProcessor assetRequestProcessor) {
//ч
//    }
//
//    @Override
//    public void passAssets(AssetProcessor assets) {
//
//    }
//
//    @Override
//    public Obstacle create(BodyHandler bodyHandler, Vector2 pos, float width, float height) {
//        if(grassGroup == null) createSingleBody(bodyHandler);
//        return new Grass(bodyHandler, pos, width, height);
//    }
//
//    private void createSingleBody(BodyHandler bodyHandler) {
//        grassGroup =  new GrassGroup(bodyHandler);
//    }
//
//
//    private class GrassGroup implements Area {
//        Body body;
//
//        private final HashMap<Actor, Integer> faded = new HashMap<>();
//
//        GrassGroup(BodyHandler bodyHandler) {
//            body = bodyHandler.requestCreation(grassGroup, Vector2.Zero, BodyDef.BodyType.StaticBody);
//        }
//
//        @Override
//        public void collusionRespond(Entity participant) {
//            if(!(participant instanceof Actor))
//                return;
//
//            Actor actor = (Actor) participant;
//
//            if(faded.containsKey(actor)){
//                faded.put(actor, faded.get(actor) + 1);
//                return;
//            }
//            else
//                faded.put(actor, 0);
//
//            Color fadedColor = new Color(actor.getColor());
//
//            if(participant instanceof PlayableTank)
//                fadedColor.a = 0.5f;
//            else
//                fadedColor.a = 0f;
//            actor.addAction(Actions.color(fadedColor, 0.3f));
//        }
//
//        @Override
//        public void endContactRespond(Entity participant) {
//            if(!(participant instanceof Actor))
//                return;
//
//            Actor actor = (Actor) participant;
//
//            if(faded.get(actor) != null && faded.get(actor) != 0) {
//                faded.put(actor, faded.get(actor) - 1);
//                return;
//            }
//
//            faded.remove(actor);
//
//            Color fadedColor = new Color(actor.getColor());
//            fadedColor.a = 1f;
//
//            actor.addAction(Actions.color(fadedColor, 0.3f));
//        }
//
//        @Override
//        public Body getBody() {
//            return body;
//        }
//    }
//
//    private class Grass extends Obstacle {
//        Grass(BodyHandler bodyHandler,  Vector2 pos, float width, float height) {
//            super(BitCategories.AREA, pos, width, height);
//
//            body = bodyHandler.requestCreation(this, pos.add(width/2, height/2), BodyDef.BodyType.StaticBody);
//
//            PolygonShape shape = new PolygonShape();
//            shape.setAsBox(width/8, height/8);
//
//            bodyHandler.createFixture(getBody(), grassGroup, shape, BitCategories.AREA.bit(), BitCategories.ALL.bit(), true, 0.1f, 0);
//
//            setScale(1.25f);
//        }
//    }
//
//}
