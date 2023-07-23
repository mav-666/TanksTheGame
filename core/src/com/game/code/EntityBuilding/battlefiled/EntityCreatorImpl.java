package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.Summoners.SummonerType;
import com.game.code.EntityBuilding.Summoners.EntitySummonerProvider;
import com.game.code.EntityBuilding.SummoningDirector;
import com.game.code.components.SummonsComponent;
import com.game.code.components.SummonsNowComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;

public class EntityCreatorImpl implements EntityCreator {

    private final EntitySummonerProvider provider;
    private final Entity creationSettings;

    private SummonerType summonerType = SummonerType.Default;

    public EntityCreatorImpl(EntitySummonerProvider provider) {
        this.provider = provider;

        EntityBuilder entityBuilder = provider.getEntityBuilder();
        entityBuilder.build("");

        creationSettings = entityBuilder.getEntity();
    }

    @Override
    public Entity createEntityOn(float x, float y, float zIndex, String entityName) {
        Mappers mappers = Mappers.getInstance();
        Engine engine = provider.getEntityBuilder().getEngine();

        TransformComponent transform = mappers.getOrCreate(TransformComponent.class, creationSettings, engine);
        transform.position.set(x, y);
        transform.zIndex = zIndex;

        SummonsNowComponent summons = mappers.getOrCreate(SummonsNowComponent.class, creationSettings, engine);
        summons.entityName = entityName;
        summons.summonerType = summonerType.name();

        SummoningDirector summoningDirector = provider.provide(summonerType);

        return summoningDirector.summonBy(creationSettings);
    }

    @Override
    public void clearSettings() {
        creationSettings.remove(SummonsNowComponent.class);
        summonerType = SummonerType.Default;
    }

    @Override
    public SummonsComponent getCreationSettings() {
        return Mappers.getInstance().get(SummonsNowComponent.class, creationSettings);
    }

    @Override
    public void setSummonerType(SummonerType summonerType) {
        this.summonerType = summonerType;
    }
}
