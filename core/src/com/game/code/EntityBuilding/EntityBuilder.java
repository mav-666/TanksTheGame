package com.game.code.EntityBuilding;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.game.code.utils.Mappers;

public abstract class EntityBuilder {

    private final Engine engine;

    protected Entity entity;
    public EntityBuilder(Engine engine) {
        this.engine = engine;


    }

    public <T extends Component> T getComponent(Class<T> componentType) {
        return Mappers.getOrCreate(componentType, entity);
    }

    public <T extends Component> boolean hasComponent(Class<T> componentType) {
        return Mappers.has(componentType, entity);
    }

    public void build(String entityName) {
        entity = engine.createEntity();
    }

    public Engine getEngine() {
        return engine;
    }

    public Entity getEntity() {
        return entity;
    }
}
