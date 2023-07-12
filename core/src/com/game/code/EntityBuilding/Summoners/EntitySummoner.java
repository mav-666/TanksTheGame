package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.SummoningDirector;
import com.game.code.components.BodyComponent;
import com.game.code.components.SummonsNowComponent;
import com.game.code.components.TransformComponent;

public class EntitySummoner extends EntityBuilderSummoner implements SummoningDirector {

    public EntitySummoner(EntityBuilder entityBuilder, Engine engine) {
        super(entityBuilder, engine);
    }

    @Override
    public Entity summonBy(Entity summoner) {
        String entityName = mappers.get(SummonsNowComponent.class).get(summoner).entityName;

        entityBuilder.build(entityName);

        initTransformBy(summoner);

        engine.addEntity(entityBuilder.getEntity());

        return entityBuilder.getEntity();
    }

    protected void initTransformBy(Entity summoner) {
        TransformComponent entityTransform = entityBuilder.getComponent(TransformComponent.class);
        TransformComponent summonerTransform = mappers.get(TransformComponent.class).get(summoner);
        Vector2 offset = mappers.get(SummonsNowComponent.class).get(summoner).offset;

        entityTransform.position.set(summonerTransform.position)
                .add(offset);
        entityTransform.degAngle = summonerTransform.degAngle;
        entityTransform.zIndex += summonerTransform.zIndex;

        if(entityBuilder.hasComponent(BodyComponent.class))
            entityBuilder.getComponent(BodyComponent.class).body.setTransform(summonerTransform.position, summonerTransform.degAngle);
    }
}
