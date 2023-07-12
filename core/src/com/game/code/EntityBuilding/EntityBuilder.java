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

    public void build(String entityName) {
        entity = engine.createEntity();
    }

    public <T extends Component> T getComponent(Class<T> componentType) {
        ComponentMapper<T> mapper = mappers.get(componentType);
        T component;

        if(mapper.has(entity))
            component = mapper.get(entity);
        else {
            component = engine.createComponent(componentType);
            entity.add(component);
        }

        return component;
    }

    public <T extends Component> boolean hasComponent(Class<T> componentType) {
        ComponentMapper<T> mapper = mappers.get(componentType);
        T component;

        return mapper.has(entity);
    }

    public Engine getEngine() {
        return engine;
    }

    public Entity getEntity() {
        return entity;
    }
}
