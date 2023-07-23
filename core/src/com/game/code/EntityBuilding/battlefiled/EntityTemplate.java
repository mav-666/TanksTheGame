package com.game.code.EntityBuilding.battlefiled;

import com.game.code.EntityBuilding.Summoners.SummonerType;

public class EntityTemplate {

    private final SummonerType summonerType;
    private final String entityName;

    public EntityTemplate(SummonerType summonerType, String entityName) {
        this.summonerType = summonerType;
        this.entityName = entityName;
    }

    public EntityTemplate(SummonerType summonerType) {
        this(summonerType, "");
    }

    public SummonerType getSummonerType() {
        return summonerType;
    }

    public String getEntityName() {
        return entityName;
    }
}
