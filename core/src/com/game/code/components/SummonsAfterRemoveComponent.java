package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.game.code.EntityBuilding.SummonerType;

public class SummonsAfterRemoveComponent implements Component, Pool.Poolable {
    public String summonerType = SummonerType.Default.name();
    public String entityName = "";

    @Override
    public void reset() {
        summonerType = SummonerType.Default.name();
        entityName = "";
    }
}
