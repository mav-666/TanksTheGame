package com.game.code;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.code.Entity.Entity;

public class ShareInfoContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Entity A = ((BodyData) contact.getFixtureA().getUserData()).owner;
        Entity B = ((BodyData) contact.getFixtureB().getUserData()).owner;



        A.collusionRespond(B);
        B.collusionRespond(A);
    }

    @Override
    public void endContact(Contact contact) {
        Entity A = ((BodyData) contact.getFixtureA().getUserData()).owner;
        Entity B = ((BodyData) contact.getFixtureB().getUserData()).owner;

        A.endContactRespond(B);
        B.endContactRespond(A);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        if(((BodyData) contact.getFixtureA().getUserData()).flaggedForDispose ||
                ((BodyData) contact.getFixtureB().getUserData()).flaggedForDispose) contact.setEnabled(false);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
