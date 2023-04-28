package com.game.code.BattleField;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.game.code.AssetManagment.AssetProcessor;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.Tank.Tank;
import com.game.code.Tank.TankData;
import com.game.code.Tank.TankFactory;
import com.game.code.utils.scene2d.TextureActor;


public class TankBuilder extends BattleFieldBuilderDecorator implements AssetRequest {
    private final TankFactory tankFactory;
    private final Array<TankData> tanksData;
    private final Group tanks;

    private final Group spawns;


    public TankBuilder(TankBuilderData tankBuilderData, BattleFieldBuilder battleFieldBuilder) {
        super(battleFieldBuilder);

        this.tanksData = tankBuilderData.tanksData;
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
    public void request(AssetProcessor assetRequestProcessor) {
        super.request(assetRequestProcessor);

        assetRequestProcessor.receiveRequest("TanksTheGame.atlas", TextureAtlas.class, this);

        tanks.getChildren().forEach((a) -> ((AssetRequest)a).request(assetRequestProcessor));

    }

    @Override
    public void passAssets(AssetProcessor assets) {

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
        tanksData.forEach((tankData -> {
            Vector2 space = getFreeSpace().stream().toList().get(getRandom().nextInt(getFreeSpace().size()));
            getFreeSpace().remove(space);

            spawns.addActor(createSpawn(space));
            tanks.addActor(createTank(tankData, space));
        }));

    }

    private TextureActor createSpawn(Vector2 pos) {
        TextureActor spawn = new TextureActor();
        spawn.setSize(getBattleFieldTileWidth(), getBattleFieldTileHeight());
        spawn.setPosition(pos.x, pos.y);
        spawn.setOrigin(spawn.getWidth()/2, spawn.getHeight()/2);

        spawn.setScale(0.75f);

        return spawn;
    }

    private Tank createTank(TankData tankData, Vector2 pos) {
        Tank tank = tankFactory.createTank(getBattleFieldBodyHandler(),
                pos, getBattleFieldTileWidth(), getBattleFieldTileHeight(),
                tankData.headData, tankData.cabData);
        tank.setName(tankData.id);
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
