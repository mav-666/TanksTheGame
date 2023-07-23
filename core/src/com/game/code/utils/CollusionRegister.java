package com.game.code.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;
import com.game.code.components.*;

public class CollusionRegister implements ContactListener {

    private final Mappers mappers = Mappers.getInstance();
    private final Engine engine;

    public CollusionRegister(Engine engine) {
        this.engine = engine;

    }

    @Override
    public void beginContact(Contact contact) {
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();

    if(A.getBody().getUserData() instanceof Entity Ae
        && B.getBody().getUserData() instanceof Entity Be) {

            addStartCollusion(Ae, Be);
            addCollusionComponent(Ae, Be);
        }
    }

    private void addStartCollusion(Entity A, Entity B) {
        ComponentMapper<StartCollusionComponent> startCollusionM = mappers.getMapper(StartCollusionComponent.class);

        if(startCollusionM.has(A)) {
            startCollusionM.get(A).involved.add(B);
        }

        if(startCollusionM.has(B)) {
            startCollusionM.get(B).involved.add(A);
        }

        A.add(engine.createComponent(StartsCollusionComponent.class));
        B.add(engine.createComponent(StartsCollusionComponent.class));
    }

    private void addCollusionComponent(Entity A, Entity B) {
        ComponentMapper<CollusionComponent> collusionM = mappers.getMapper(CollusionComponent.class);

        if(collusionM.has(A)) {
            collusionM.get(A).involved.add(B);
        }

        if(collusionM.has(B)) {
            collusionM.get(B).involved.add(A);
        }

        A.add(engine.createComponent(CollidesComponent.class));
        B.add(engine.createComponent(CollidesComponent.class));
    }

    @Override
    public void endContact(Contact contact) {
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();

        if(A.getBody().getUserData() instanceof Entity Ae
                && B.getBody().getUserData() instanceof Entity Be) {

            endCollusion(Ae, Be);
            addEndsCollusionComponent(Ae, Be);
        }
    }

    private void endCollusion(Entity A, Entity B) {
        ComponentMapper<CollusionComponent> collusionM = mappers.getMapper(CollusionComponent.class);

        if(collusionM.has(A))
            collusionM.get(A).involved.removeValue(B, true);

        if(collusionM.has(B))
            collusionM.get(B).involved.removeValue(A, true);

        A.remove(CollidesComponent.class);
        B.remove(CollidesComponent.class);
    }

    private void addEndsCollusionComponent(Entity A, Entity B) {
        ComponentMapper<EndCollusionComponent> endCollusionM = mappers.getMapper(EndCollusionComponent.class);

        if(endCollusionM.has(A))
            endCollusionM.get(A).involved.add(B);

        if(endCollusionM.has(B))
            endCollusionM.get(B).involved.add(A);

        A.add(engine.createComponent(EndsCollusionComponent.class));
        B.add(engine.createComponent(EndsCollusionComponent.class));

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
