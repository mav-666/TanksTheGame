package com.game.code.EntityBuilding;

import com.badlogic.ashley.core.Entity;

public interface SummoningDirector {

    Entity summonBy(Entity summoner);
}
