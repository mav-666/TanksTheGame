package com.game.code.EntityBuilding;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.components.BodyComponent;
import com.game.code.utils.Mappers;

public class EntityUserDataSetter implements EntityListener {

    @Override
    public void entityAdded(Entity entity) {
        Body body = Mappers.getInstance().get(BodyComponent.class).get(entity).body;

        if(body != null) {
            body.setUserData(entity);
        }

    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
