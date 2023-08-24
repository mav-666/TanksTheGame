package com.game.code.systems.HUD;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.components.GainsPointsComponent;
import com.game.code.components.IdComponent;
import com.game.code.components.NameComponent;
import com.game.code.components.PlayerComponent;
import com.game.code.utils.Mappers;

public class ScoreSystem extends IteratingSystem {

    private final ObjectMap<String, Integer> playerScore = new ObjectMap<>();

    public ScoreSystem() {
        super(Family.all(GainsPointsComponent.class, NameComponent.class)
                .one(IdComponent.class, PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        String id = Mappers.has(PlayerComponent.class, entity)? "player" : Mappers.get(IdComponent.class, entity).id;
        int points = Mappers.get(GainsPointsComponent.class, entity).points;

        if(!playerScore.containsKey(id))
            playerScore.put(id, 0);

        playerScore.put(id, playerScore.get(id) + points);

        entity.remove(GainsPointsComponent.class);
    }

    public ObjectMap<String, Integer> getScore() {
        return playerScore;
    }
}
