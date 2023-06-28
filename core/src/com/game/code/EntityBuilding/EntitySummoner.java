package com.game.code.EntityBuilding;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.components.BodyComponent;
import com.game.code.components.SummonsComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;

public class EntitySummoner implements SummoningDirector {

    private final Mappers mappers;
    private final EntityBuilder entityBuilder;

    private Entity summoner;

    public EntitySummoner(EntityBuilder entityBuilder) {
        this.entityBuilder = entityBuilder;

        mappers = Mappers.getInstance();
    }

    @Override
    public Entity summonBy(Entity summoner) {
        this.summoner = summoner;
        String entityName = mappers.get(SummonsComponent.class).get(summoner).entityName;

        entityBuilder.build(entityName);

        initPosition();

        return entityBuilder.getEntity();
    }

    private void initPosition() {
        Vector2 summonerPos = mappers.get(TransformComponent.class).get(summoner).position;
        Vector2 entityPos = entityBuilder.getComponent(TransformComponent.class).position;
        Body entityBody = entityBuilder.getComponent(BodyComponent.class).body;

        entityBody.setTransform(summonerPos, entityBody.getAngle());
        entityPos.set(summonerPos);
    }
}
