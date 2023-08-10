package com.game.code.EntityBuilding;

import com.badlogic.ashley.core.Entity;
import com.game.code.EntityBuilding.Summoners.SummonerType;

public interface SummoningDirector {

    Entity summonBy(Entity summoner);

    SummonerType getType();
}
