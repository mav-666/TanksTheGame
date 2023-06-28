package com.game.code.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;

import java.util.Comparator;

public class ZComparator implements Comparator<Entity> {

    private final Mappers mappers;

    ZComparator() {
        mappers = Mappers.getInstance();
    }

    @Override
    public int compare(Entity o1, Entity o2) {
        ComponentMapper<TransformComponent> transformM= mappers.get(TransformComponent.class);
        float pos1 = transformM.get(o1).zIndex;
        float pos2 = transformM.get(o2).zIndex;

        return (int) Math.signum(pos2 - pos1);
    }
}
