package com.game.code.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.components.HasFriendsComponent;

import java.util.Optional;

public class FriendsMaker {

    private final Mappers mappers = Mappers.getInstance();
    private final BodySearcher bodySearcher;

    public FriendsMaker() {

        bodySearcher = new BodySearcher();
    }

    public void makeFriendsTo(Entity friend, Entity newFriend) {
        ComponentMapper<HasFriendsComponent> friendlyM = mappers.get(HasFriendsComponent.class);

        Optional<Body> friendBody = bodySearcher.searchForBodyIn(friend);
        Optional<Body> newFriendBody = bodySearcher.searchForBodyIn(newFriend);

        if(friendBody.isEmpty() || newFriendBody.isEmpty())
            return;

        Entity bodyOwner = getBodyOwner(friendBody.get());

        if(friendlyM.has(bodyOwner))
            makeFriends(friendBody.get(), newFriendBody.get(), friendlyM.get(bodyOwner).friendlyGroup);

    }



    private Entity getBodyOwner(Body body) {
        return ((Entity) body.getUserData());
    }


    private void makeFriends(Body friendA, Body friendB, short friendGroup) {
        friendA.getFixtureList().first().getFilterData().groupIndex =
                friendB.getFixtureList().first().getFilterData().groupIndex =
                        friendGroup;
    }

}
