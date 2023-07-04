package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.SummoningDirector;

public class EntitySummonerProvider {

    private static final String DEFAULT = "default";

    private Engine engine;
    private EntityBuilder entityBuilder;
    private ObjectMap<String, SummoningDirector> summoners;

    public EntitySummonerProvider(Engine engine, EntityBuilder entityBuilder) {
        this.engine = engine;
        this.entityBuilder = entityBuilder;

        summoners = new ObjectMap<>();
        summoners.put(DEFAULT, new EntitySummoner(entityBuilder, engine));
    }

    public SummoningDirector provide(String complexEntity) {
        if(summoners.containsKey(complexEntity))
            return summoners.get(complexEntity);
        else
            return createSummoner(complexEntity);
    }

    private SummoningDirector createSummoner(String complexEntity) {
        switch(complexEntity) {
            case "Tank" -> summoners.put(complexEntity, new TankSummoner(entityBuilder, engine));

            default -> {
                return summoners.get(DEFAULT);
            }
        };
        return summoners.get(complexEntity);
    }

    public EntityBuilder getEntityBuilder() {
        return entityBuilder;
    }
}
