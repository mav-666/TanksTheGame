package com.game.code.EntityBuilding.battlefiled;

public class PercentageFiller {

    private Placer placer;
    private EntityTemplate filling;

    private float percent = 50;

    float width = 50;
    float height = 50;

    public void fillWith(Placer placer, BattleFieldTemplate battleFieldTemplate) {
        if(!placer.hasNext()) return;

        placer.nextSpot();

    }


    void setFilling(EntityTemplate entitySpawn) {

    }
}
