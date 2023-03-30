package com.game.code.BattleField;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.Tank.Tank;
import com.game.code.Tank.TankData;
import com.game.code.Tank.TankFactory;
import com.game.code.TextureActor;

import java.util.HashMap;

public class TankBuilder extends BattleFiledBuilderDecorator implements AssetRequest {
    private final TankFactory tankFactory;
    private final HashMap<String, TankData> tanksData;
    private final Group tanks;

    private final Group spawns;


    public TankBuilder(HashMap<String, TankData> tanksData, BattleFieldBuilder battleFieldBuilder) {
        super(battleFieldBuilder);

        this.tanksData = tanksData;
        tankFactory = new TankFactory();
        tanks = new Group();

        spawns = new Group() {
            @Override
            public String toString() {
                return "spawns";
            }
        };
    }

    @Override
    public void request(AssetRequestProcessor assetRequestProcessor) {
        super.request(assetRequestProcessor);

        assetRequestProcessor.receiveRequest("TanksTheGame.atlas", TextureAtlas.class, this);

        tanks.getChildren().forEach((a) -> ((AssetRequest)a).request(assetRequestProcessor));

    }

    @Override
    public void passAssets(AssetRequestProcessor assets) {

        TextureAtlas.AtlasRegion tileTexture =
                assets.get("TanksTheGame.atlas", TextureAtlas.class).findRegion("spawn");

        spawns.getChildren().forEach((actor) -> ((TextureActor) actor).setTexture(tileTexture));
    }

    @Override
    public void buildBattleField() {
        super.buildBattleField();
        buildTanks();
    }

    private void buildTanks() {
        for(String id : tanksData.keySet()) {
            Vector2 space = getFreeSpace().stream().toList().get(getRandom().nextInt(getFreeSpace().size()));
            getFreeSpace().remove(space);

            spawns.addActor(createSpawn(space));
            tanks.addActor(createTank(id, space));

        }
    }

    private TextureActor createSpawn(Vector2 pos) {
        TextureActor spawn = new TextureActor();
        spawn.setSize(getBattleFieldTileWidth(), getBattleFieldTileHeight());
        spawn.setPosition(pos.x, pos.y);
        spawn.setOrigin(spawn.getWidth()/2, spawn.getHeight()/2);

        spawn.setScale(0.75f);

        return spawn;
    }

    private Tank createTank(String id, Vector2 pos) {
        Tank tank = tankFactory.createTank(getBattleFieldBodyHandler(),
                pos, getBattleFieldTileWidth(), getBattleFieldTileHeight(),
                tanksData.get(id).headData, tanksData.get(id).cabData);
        tank.setId(id);
        return tank;
    }

    public BattleField createBattleField() {
        BattleField battleField = super.createBattleField();
        battleField.addActorAfter(battleField.getChildren().first(), spawns);
        battleField.addActor(tanks);

        return battleField;
    }

    public Group getTanks() {
        return tanks;
    }
}
