package com.game.code.systems.Summon;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.utils.Array;
import com.game.code.components.*;
import com.game.code.utils.Mappers;

public class SummonsAfterCollusionSystem extends EntitySystem implements EntityListener {
    public static final Family FAMILY = Family.all(StartCollusionComponent.class, StartsCollusionComponent.class,
            SummonsAfterCollusionComponent.class).get();

    private final Array<Entity> summoningEntities = new Array<>();

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.addEntityListener(FAMILY, this);
    }

    @Override
    public void entityAdded(Entity entity) {
        summoningEntities.add(entity);

    }

    @Override
    public void entityRemoved(Entity entity) {

    }

    @Override
    public void update(float deltaTime) {
        summoningEntities.forEach(entity ->
            Mappers.get(StartCollusionComponent.class, entity).involved.forEach(involved -> {
                ComponentMapper<TransformComponent> transformM = Mappers.getMapper(TransformComponent.class);

                SummonsAfterCollusionComponent afterCollusion = Mappers.get(SummonsAfterCollusionComponent.class, entity);
                SummonsNowComponent summons = getEngine().createComponent(SummonsNowComponent.class);

                summons.summonerType = afterCollusion.summonerType;
                summons.entityName = afterCollusion.entityName;
                summons.offset.set(transformM.get(entity).position).sub(transformM.get(involved).position).scl(-1, -1);

                entity.add(summons);
            })
        );

        summoningEntities.clear();
    }
}
