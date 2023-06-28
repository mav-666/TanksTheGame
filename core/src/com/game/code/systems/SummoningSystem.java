package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.EntityBuilding.SummoningDirector;
import com.game.code.components.SummonsComponent;
import com.game.code.components.TransformComponent;

public class SummoningSystem extends IteratingSystem {

    private final SummoningDirector summoningDirector;

    public SummoningSystem(SummoningDirector summoningDirector) {
        super(Family.all(SummonsComponent.class, TransformComponent.class).get());

        this.summoningDirector = summoningDirector;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        getEngine().addEntity(summoningDirector.summonBy(entity));
        entity.remove(SummonsComponent.class);
    }
}
