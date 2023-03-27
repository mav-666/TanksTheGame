package com.game.code;

import com.badlogic.gdx.physics.box2d.*;
import com.game.code.Entity.Entity;

import java.util.HashSet;


public class DisposeAfterContact implements ContactListener, BodyDisposal {
    ContactListener contactListener;

    HashSet<Entity> destroyedEntities;
    World world;

    public DisposeAfterContact(ContactListener contactListener, World world) {
        this.contactListener = contactListener;
        this.world = world;

        destroyedEntities = new HashSet<>();
    }


    @Override
    public void beginContact(Contact contact) {
        if(contact.getFixtureA() != null && contact.getFixtureB() != null)
            contactListener.beginContact(contact);
    }

    @Override
    public void endContact(Contact contact) {
        contactListener.endContact(contact);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        contactListener.preSolve(contact, oldManifold);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        contactListener.postSolve(contact, impulse);

        Entity A = ((BodyData) contact.getFixtureA().getUserData()).owner;
        Entity B = ((BodyData) contact.getFixtureB().getUserData()).owner;

            if (A.getBody().getUserData() == null || ((BodyData) A.getBody().getUserData()).flaggedForDispose)
                destroyedEntities.add(A);
            if (B.getBody().getUserData() == null || ((BodyData) B.getBody().getUserData()).flaggedForDispose)
                destroyedEntities.add(B);
    }

    @Override
    public void destroyAllBodies() {
        destroyedEntities.forEach(Entity::destroy);
        destroyedEntities.clear();
    }
}
