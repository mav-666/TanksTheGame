package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.SummoningDirector;
import com.game.code.components.BodyComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;

public abstract class EntityBuilderSummoner implements SummoningDirector {

    protected final EntityBuilder entityBuilder;
    protected final Engine engine;

    protected final Mappers mappers;

    protected Entity summoner;

    EntityBuilderSummoner(EntityBuilder entityBuilder, Engine engine) {
        this.entityBuilder = entityBuilder;
        this.engine = engine;

        mappers = Mappers.getInstance();
    }
}
