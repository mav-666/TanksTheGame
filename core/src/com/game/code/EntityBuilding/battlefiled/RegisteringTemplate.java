package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectSet;

import java.util.Iterator;

public class RegisteringTemplate implements BattleFieldTemplate {

    private BattleFieldTemplate battleFieldTemplate;

    private ObjectSet<Vector2> occupied = new ObjectSet<>();

    protected RegisteringTemplate(BattleFieldTemplate battleFieldTemplate) {
        this.battleFieldTemplate = battleFieldTemplate;
    }

    @Override
    public void put(Vector2 position, EntityTemplate entitySpawn) {
        battleFieldTemplate.put(position, entitySpawn);

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
    public Iterator<EntityTemplate> getEntitiesOn(Vector2 position) {
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
