package com.game.code;

import com.badlogic.gdx.physics.box2d.*;
import com.game.code.Entity.Entity;

import java.util.HashSet;


public class DisposeAfterContact implements ContactListener, BodyDisposal {
    ContactListener contactListener;

    HashSet<Body> destroyedBodies;
    World world;

    public DisposeAfterContact(ContactListener contactListener, World world) {
        this.contactListener = contactListener;
        this.world = world;

        destroyedBodies = new HashSet<>();
    }


    @Override
    public void beginContact(Contact contact) {
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

        Entity A = ((Entity) contact.getFixtureA().getUserData());
        Entity B = ((Entity) contact.getFixtureB().getUserData());

        if(((BodyData) A.getBody().getUserData()).flaggedForDispose)
            destroyedBodies.add(A.getBody());
        if(((BodyData) B.getBody().getUserData()).flaggedForDispose)
            destroyedBodies.add(B.getBody());
    }

    @Override
    public void destroyAllBodies() {
        destroyedBodies.forEach(world::destroyBody);
        destroyedBodies.clear();
    }
}
