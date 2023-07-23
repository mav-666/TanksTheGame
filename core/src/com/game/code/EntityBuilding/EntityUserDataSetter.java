package com.game.code.EntityBuilding;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.components.BodyComponent;
import com.game.code.components.IdComponent;
import com.game.code.components.ProjectileTemplateComponent;
import com.game.code.utils.Mappers;

public class EntityUserDataSetter implements EntityListener {
    public final static Family FAMILY = Family.all(BodyComponent.class).get();

    @Override
    public void entityAdded(Entity entity) {
        Body body = Mappers.getInstance().get(BodyComponent.class, entity).body;

        if(body != null) {
            body.setUserData(entity);
        }

    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
