package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.SummoningDirector;
import com.game.code.components.BodyComponent;
import com.game.code.components.SummonsComponent;
import com.game.code.components.TransformComponent;

public class EntitySummoner extends EntityBuilderSummoner implements SummoningDirector {

    private Entity summoner;

    public EntitySummoner(EntityBuilder entityBuilder, Engine engine) {
        super(entityBuilder, engine);
    }

    @Override
    public Entity summonBy(Entity summoner) {
        this.summoner = summoner;

        String entityName = mappers.get(SummonsComponent.class).get(summoner).entityName;

        entityBuilder.build(entityName);

        initPosition(mappers.get(TransformComponent.class).get(summoner).position);

        engine.addEntity(entityBuilder.getEntity());

        return entityBuilder.getEntity();
    }

    protected void initPosition(Vector2 pos) {
        Vector2 entityPos = entityBuilder.getComponent(TransformComponent.class).position;

        entityPos.set(pos);

        if(entityBuilder.hasComponent(BodyComponent.class))
            entityBuilder.getComponent(BodyComponent.class).body.setTransform(pos, 0);
    }
}
