package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.SummoningDirector;
import com.game.code.utils.Mappers;

public abstract class EntityBuilderSummoner implements SummoningDirector {

    protected final EntityBuilder entityBuilder;
    protected final Engine engine;

    protected final Mappers mappers = Mappers.getInstance();

    EntityBuilderSummoner(EntityBuilder entityBuilder, Engine engine) {
        this.entityBuilder = entityBuilder;
        this.engine = engine;
    }
}
