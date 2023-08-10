package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.game.code.EntityBuilding.Summoners.SummonerType;
import com.game.code.components.SummonsComponent;

public interface EntityCreator {

    Entity createEntityOn(float x, float y, float zIndex, String entityName);

    default Entity createEntityOn(float x, float y, String entityName) {
        return createEntityOn(x, y, 0, entityName);
    }

    void clearSettings();

    <T extends Component> T  getCreationSettings(Class<T> component);

    SummonsComponent getSummoningSettings();

    void setSummonerType(SummonerType summonerType);
}
