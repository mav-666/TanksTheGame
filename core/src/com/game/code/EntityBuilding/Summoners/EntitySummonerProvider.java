package com.game.code.EntityBuilding.Summoners;

import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.EntityBuilding.SummoningDirector;

public class EntitySummonerProvider {

    private final ObjectMap<SummonerType, SummoningDirector> summoners = new ObjectMap<>();

    public EntitySummonerProvider(SummoningDirector defaultSummoner) {
        summoners.put(SummonerType.Default, defaultSummoner);
    }

    public SummoningDirector provide(SummonerType summonerType) {
        return summoners.get(summonerType, summoners.get(SummonerType.Default));
    }

    public void add(SummoningDirector summoningDirector) {
        summoners.put(summoningDirector.getType(), summoningDirector);
    }
}
