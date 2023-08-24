package com.game.code.systems.Summon;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.EntityBuilding.Summoners.SummonerType;
import com.game.code.EntityBuilding.Summoners.EntitySummonerProvider;
import com.game.code.EntityBuilding.SummoningDirector;
import com.game.code.components.SummonsNowComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;

public class SummoningSystem extends IteratingSystem {

    private final EntitySummonerProvider summonerProvider;

    public SummoningSystem(EntitySummonerProvider summonerProvider) {
        super(Family.all(SummonsNowComponent.class, TransformComponent.class).get());

        this.summonerProvider = summonerProvider;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SummonerType summonerType = SummonerType.valueOf(Mappers.get(SummonsNowComponent.class, entity).summonerType);
        SummoningDirector summoningDirector = summonerProvider.provide(summonerType);

        summoningDirector.summonBy(entity);
        entity.remove(SummonsNowComponent.class);
    }
}
