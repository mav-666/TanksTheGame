package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.Summoners.EntitySummonerProvider;
import com.game.code.EntityBuilding.SummoningDirector;
import com.game.code.components.SummonsComponent;
import com.game.code.components.TransformComponent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Iterator;

public class BattlefieldFactoryImpl implements BattlefieldFactory {

    private final EntitySummonerProvider provider;

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
        Iterator<Pair<Float, EntityTemplate>> entityTemplates = battleFieldTemplate.getEntitiesOn(spot);

        while (entityTemplates.hasNext()) {
            Pair<Float, EntityTemplate>  entityTemplate = entityTemplates.next();
            createEntityOn(spot, entityTemplate.getLeft(), entityTemplate.getRight());
        }
    }

    private void createEntityOn(Vector2 spot, float zIndex, EntityTemplate entityTemplate) {
        SummoningDirector summoningDirector = provider.provide(entityTemplate.getSummonerType());

        EntityBuilder entityBuilder = provider.getEntityBuilder();

        entityBuilder.build("");

        TransformComponent transform = entityBuilder.getComponent(TransformComponent.class);
        transform.position.set(spot.x, spot.y);
        transform.zIndex = zIndex;

        SummonsComponent summons = entityBuilder.getComponent(SummonsComponent.class);
        summons.entityName = entityTemplate.getEntityName();
        summons.summonerType = entityTemplate.getSummonerType().name();

        Entity summoner = entityBuilder.getEntity();

        summoningDirector.summonBy(summoner);
        entityBuilder.getEngine().removeEntity(summoner);

    }
}
