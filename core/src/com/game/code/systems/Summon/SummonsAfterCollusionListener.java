package com.game.code.systems.Summon;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.game.code.components.*;
import com.game.code.utils.Mappers;

public class SummonsAfterCollusionListener implements EntityListener {
    public static final Family FAMILY = Family.all(StartsCollusionComponent.class,
            SummonsAfterCollusionComponent.class).get();

    private final Engine engine;

    public SummonsAfterCollusionListener(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void entityAdded(Entity entity) {
        Mappers.get(StartCollusionComponent.class, entity).involved.forEach(involved -> {
            Gdx.app.log("a", "gag");
            ComponentMapper<TransformComponent> transformM = Mappers.getMapper(TransformComponent.class);

            SummonsAfterCollusionComponent afterCollusion = Mappers.get(SummonsAfterCollusionComponent.class, entity);
            SummonsNowComponent summons = engine.createComponent(SummonsNowComponent.class);

            summons.summonerType = afterCollusion.summonerType;
            summons.entityName = afterCollusion.entityName;
            summons.offset.set(transformM.get(entity).position).sub(transformM.get(involved).position).scl(-1, -1);

            entity.add(summons);
        });
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
