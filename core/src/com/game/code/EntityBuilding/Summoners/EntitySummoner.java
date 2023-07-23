package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.SummoningDirector;
import com.game.code.components.*;

public class EntitySummoner extends EntityBuilderSummoner implements SummoningDirector {

    private String entityName;

    public EntitySummoner(EntityBuilder entityBuilder, Engine engine) {
        super(entityBuilder, engine);
    }

    @Override
    public Entity summonBy(Entity summoner) {
        entityName = mappers.get(SummonsNowComponent.class, summoner).entityName;

        build();

        init(summoner);

        engine.addEntity(entityBuilder.getEntity());

        return entityBuilder.getEntity();
    }

    protected void build() {
        entityBuilder.build(entityName);
    }

    protected void init(Entity summoner) {
        initTransformBy(summoner);
    }

    protected void initTransformBy(Entity summoner) {
        SummonsComponent summonsC = mappers.get(SummonsNowComponent.class, summoner);

        TransformComponent entityTransform = entityBuilder.getComponent(TransformComponent.class);
        TransformComponent summonerTransform = mappers.get(TransformComponent.class, summoner);

        entityTransform.position.set(summonerTransform.position).add(summonsC.offset);
        entityTransform.degAngle = summonsC.degAngle;
        entityTransform.zIndex += summonerTransform.zIndex;

        initScale(summonsC);

        if(entityBuilder.hasComponent(BodyComponent.class))
            entityBuilder.getComponent(BodyComponent.class).body.setTransform(summonerTransform.position, summonerTransform.degAngle);
    }

    protected void initScale(SummonsComponent summonsComponent) {
        if(!entityBuilder.hasComponent(TextureComponent.class))
            return;
        
        TextureComponent texture = entityBuilder.getComponent(TextureComponent.class);
        texture.scaleX = summonsComponent.scaleX;
        texture.scaleY = summonsComponent.scaleY;
    }
}
