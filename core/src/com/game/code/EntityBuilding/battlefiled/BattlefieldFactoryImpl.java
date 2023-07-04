package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.Summoners.EntitySummonerProvider;
import com.game.code.EntityBuilding.SummoningDirector;
import com.game.code.components.SummonsComponent;
import com.game.code.components.TransformComponent;

import java.util.Iterator;

public class BattlefieldFactoryImpl implements BattlefieldFactory {

    private EntitySummonerProvider provider;

    private BattleFieldTemplate battleFieldTemplate;

    public BattlefieldFactoryImpl(EntitySummonerProvider provider) {
        this.provider = provider;
    }

    @Override
    public void create(BattleFieldTemplate battleFieldTemplate) {
        this.battleFieldTemplate = battleFieldTemplate;

        Iterator<Vector2> spots = battleFieldTemplate.getAllSpots();

        while (spots.hasNext()) {
            findOnSpot(spots.next());
        }

    }

    private void findOnSpot(Vector2 spot) {
        Iterator<EntityTemplate> entityTemplates = battleFieldTemplate.getEntitiesOn(spot);
        while (entityTemplates.hasNext())
            createEntityOn(spot,  entityTemplates.next());
    }

    private void createEntityOn(Vector2 spot, EntityTemplate entityTemplate) {
        SummoningDirector summoningDirector = provider.provide(entityTemplate.getEntityName());

        EntityBuilder entityBuilder = provider.getEntityBuilder();

        entityBuilder.build("");
        entityBuilder.getComponent(TransformComponent.class).position.set(spot);
        entityBuilder.getComponent(SummonsComponent.class).entityName = entityTemplate.getEntityName();

        Entity summoner = entityBuilder.getEntity();

        summoningDirector.summonBy(summoner);
        entityBuilder.getEngine().removeEntity(summoner);

    }
}
