package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Iterator;

public class GridTemplate implements BattleFieldTemplate {

    ObjectMap<Vector2, Array<Pair<Float, EntityTemplate>>> grid;

    public GridTemplate() {
        grid = new ObjectMap<>();
    }

    @Override
    public void put(Vector2 position, float zIndex, EntityTemplate entitySpawn) {
        if(!grid.containsKey(position))
            createCell(position);

        grid.get(position).add(Pair.of(zIndex, entitySpawn));
    }

    private void createCell(Vector2 position) {
        grid.put(position, new Array<>());
    }

    @Override
    public Iterator<Pair<Float, EntityTemplate>> getEntitiesOn(Vector2 position) {
        return grid.get(position).iterator();
    }

    @Override
    public Iterator<Vector2> getAllSpots() {
        Array<Vector2> all = new Array<>() {{
            grid.keys().forEach(this::add);
        }};

        return all.iterator();
    }

    @Override
    public void clear() {
        grid.clear();
    }
}
