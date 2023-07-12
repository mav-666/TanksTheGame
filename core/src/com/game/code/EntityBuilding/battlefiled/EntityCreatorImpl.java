package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.SummonerType;
import com.game.code.EntityBuilding.Summoners.EntitySummonerProvider;
import com.game.code.EntityBuilding.SummoningDirector;
import com.game.code.components.SummonsNowComponent;
import com.game.code.components.TransformComponent;

public class EntityCreatorImpl implements EntityCreator {

    private final EntitySummonerProvider provider;

    private SummonerType summonerType = SummonerType.Default;

    public EntityCreatorImpl(EntitySummonerProvider provider) {
        this.provider = provider;
    }

    @Override
    public void createEntityOn(Vector2 spot, float zIndex, String entityName) {
        SummoningDirector summoningDirector = provider.provide(summonerType);

        EntityBuilder entityBuilder = provider.getEntityBuilder();

        entityBuilder.build("");

        TransformComponent transform = entityBuilder.getComponent(TransformComponent.class);
        transform.position.set(spot.x, spot.y);
        transform.zIndex = zIndex;

        SummonsNowComponent summons = entityBuilder.getComponent(SummonsNowComponent.class);
        summons.entityName = entityName;
        summons.summonerType = summonerType.name();

        Entity summoner = entityBuilder.getEntity();

        summoningDirector.summonBy(summoner);
        entityBuilder.getEngine().removeEntity(summoner);

    }

    @Override
    public void setSummonerType(SummonerType summonerType) {
        this.summonerType = summonerType;
    }
}
