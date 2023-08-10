package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.ashley.core.Component;
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
    private final Engine engine;

    private SummonerType summonerType = SummonerType.Default;

    public EntityCreatorImpl(EntitySummonerProvider provider, EntityBuilder entityBuilder) {
        this.provider = provider;
        this.engine = entityBuilder.getEngine();

        entityBuilder.build("");
        creationSettings = entityBuilder.getEntity();
    }

    @Override
    public Entity createEntityOn(float x, float y, float zIndex, String entityName) {
        Mappers mappers = Mappers.getInstance();

        TransformComponent transform = mappers.getOrCreate(TransformComponent.class, creationSettings);
        transform.position.set(x, y);
        transform.zIndex = zIndex;

        SummonsNowComponent summons = mappers.getOrCreate(SummonsNowComponent.class, creationSettings);
        summons.entityName = entityName;
        summons.summonerType = summonerType.name();

        SummoningDirector summoningDirector = provider.provide(summonerType);

        return summoningDirector.summonBy(creationSettings);
    }

    @Override
    public void clearSettings() {
        creationSettings.add(engine.createComponent(SummonsNowComponent.class));
        summonerType = SummonerType.Default;
    }

    @Override
    public <T extends Component> T  getCreationSettings(Class<T> component) {
        return Mappers.getInstance().getOrCreate(component, creationSettings);
    }

    @Override
    public SummonsComponent getSummoningSettings() {
        return getCreationSettings(SummonsNowComponent.class);
    }

    @Override
    public void setSummonerType(SummonerType summonerType) {
        this.summonerType = summonerType;
    }
}
