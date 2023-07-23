package com.game.code.EntityBuilding;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.game.code.utils.Mappers;

public abstract class EntityBuilder {
    private final Mappers mappers = Mappers.getInstance();

    private final Engine engine;

    protected Entity entity;
    public EntityBuilder(Engine engine) {
        this.engine = engine;


    }

    public <T extends Component> T getComponent(Class<T> componentType) {
        return mappers.getOrCreate(componentType, entity, engine);
    }

    public <T extends Component> boolean hasComponent(Class<T> componentType) {
        return mappers.has(componentType, entity);
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
