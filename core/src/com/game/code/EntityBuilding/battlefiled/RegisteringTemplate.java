package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectSet;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Iterator;

public class RegisteringTemplate implements BattleFieldTemplate {

    private final BattleFieldTemplate battleFieldTemplate;

    private final ObjectSet<Vector2> occupied = new ObjectSet<>();

    protected RegisteringTemplate(BattleFieldTemplate battleFieldTemplate) {
        this.battleFieldTemplate = battleFieldTemplate;
    }

    @Override
    public void put(Vector2 position, float zIndex, EntityTemplate entitySpawn) {
        battleFieldTemplate.put(position, zIndex, entitySpawn);

        occupied.add(position);
    }
    @Override
    public boolean isOccupied(Vector2 position) {
        return occupied.contains(position);
    }

    @Override
    public ObjectSet<Vector2> getOccupied() {
        return occupied;
    }

    @Override
    public Iterator<Pair<Float, EntityTemplate>> getEntitiesOn(Vector2 position) {
        return battleFieldTemplate.getEntitiesOn(position);
    }


    @Override
    public Iterator<Vector2> getAllSpots() {
        return battleFieldTemplate.getAllSpots();
    }

    @Override
    public void clear() {
        battleFieldTemplate.clear();
    }
}
