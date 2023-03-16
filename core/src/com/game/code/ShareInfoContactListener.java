package com.game.code;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.code.Entity.Entity;

public class ShareInfoContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Entity A = ((Entity) contact.getFixtureA().getUserData());
        Entity B = ((Entity) contact.getFixtureB().getUserData());

        A.collusionRespond(B);
        B.collusionRespond(A);
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
