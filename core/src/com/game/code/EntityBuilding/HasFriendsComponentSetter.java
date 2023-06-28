package com.game.code.EntityBuilding;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.game.code.components.BodyComponent;
import com.game.code.components.HasFriendsComponent;
import com.game.code.utils.Mappers;

import java.util.HashSet;
import java.util.Set;

public class HasFriendsComponentSetter implements EntityListener {

    private final Set<Short> usedIndexes;
    private final Mappers mappers;

    public HasFriendsComponentSetter() {
        usedIndexes = new HashSet<>();
        mappers = Mappers.getInstance();
    }

    private short nextIndex() {
        return (short) -(usedIndexes.size() + 1);
    }

    @Override
    public void entityAdded(Entity entity) {
        ComponentMapper<HasFriendsComponent> friendlyM = mappers.get(HasFriendsComponent.class);
        ComponentMapper<BodyComponent> bodyM = mappers.get(BodyComponent.class);

        short index = nextIndex();

        friendlyM.get(entity).friendlyGroup = index;

        if(bodyM.has(entity))
            bodyM.get(entity).body.getFixtureList().first().getFilterData().groupIndex = index;

        usedIndexes.add(index);
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
