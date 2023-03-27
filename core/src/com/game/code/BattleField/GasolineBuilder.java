//package com.game.code.BattleField;
//
//import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.BodyDef;
//import com.badlogic.gdx.physics.box2d.Shape;
//import com.badlogic.gdx.physics.box2d.World;
//import com.game.code.AssetManagment.AssetRequest;
//import com.game.code.AssetManagment.AssetRequestProcessor;
//import com.game.code.Entity.BitCategories;
//import com.game.code.Entity.Breakable;
//
//public class GasolineBuilder extends ObstacleBuilder implements AssetRequest {
//    GasolineBuilder(float density, BattleFieldBuilder battleFieldBuilder) {
//        super(density, battleFieldBuilder);
//    }
//
//    @Override
//    public void request(AssetRequestProcessor assetRequestProcessor) {
//        super.request(assetRequestProcessor);
//
//        assetRequestProcessor.receiveRequest("TanksTheGame.atlas", TextureAtlas.class, this);
//    }
//
//    @Override
//    public void passAssets(AssetRequestProcessor assets) {
//        super.passAssets(assets);
//
//    }
//
//    @Override
//    protected void addObstacle() {
//        Vector2 space = getFreeSpace().stream().toList().get((int)(Math.random() * getFreeSpace().size()));
//        getFreeSpace().remove(space);
//
//        float width = (float) Math.random() * (getBattleFieldTileWidth() - 0.5f) + 0.5f;
//
//        obstacles.addActor(createObstacle(space, width, width));
//    }
//
//    protected Obstacle createObstacle(Vector2 pos, float width, float height) {
//        Obstacle gasoline = new Gasoline();
//    }
//
//
//    class Gasoline extends Obstacle implements Breakable {
//        float health;
//
//        Gasoline(World world, BodyDef.BodyType type, BitCategories category, Shape bodyShape, Vector2 pos, float width, float height) {
//            super(world, type, category, bodyShape, pos, width, height);
//        }
//
//
//
//        @Override
//        public float getHealth() {
//            return health;
//        }
//
//        @Override
//        public boolean takeDamage(float damage) {
//            health -= damage;
//
//            if(health <= 0) {
//                die();
//            }
//            return true;
//        }
//
//        @Override
//        public void die() {
//
//            flagForDispose();
//        }
//    }
//
//}
