package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.game.code.EntityBuilding.SummonerType;

public abstract class SummonsComponent implements Component, Pool.Poolable {
    public String summonerType = SummonerType.Default.name();
    public String entityName = "";
    public Vector2 offset = new Vector2();

    @Override
    public void reset() {
        summonerType = SummonerType.Default.name();
        entityName = "";
        offset = new Vector2();
    }
}
