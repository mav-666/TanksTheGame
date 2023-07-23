package com.game.code.utils;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.HashMap;

public class Mappers {
    private static Mappers instance;

    private final ObjectMap<Class<? extends Component>, ComponentMapper<? extends Component>> mappers = new ObjectMap<>();

    private Mappers() {}

    public static Mappers getInstance() {
        if(instance == null) {
            instance = new Mappers();
        }
        return instance;
    }

    public <T extends Component> T get(Class<T> componentType, Entity entity) {
        ComponentMapper<T> mapper = getMapper(componentType);
        T component = null;

        if(mapper.has(entity))
            component = mapper.get(entity);

        return component;
    }

    public <T extends Component> T getOrCreate(Class<T> componentType, Entity entity, Engine engine) {
        T component = get(componentType, entity);

        if(component == null) {
            component = engine.createComponent(componentType);
            entity.add(component);
        }
        return component;
    }

    public <T extends Component> boolean has(Class<T> componentType, Entity entity) {
        ComponentMapper<T> mapper = getMapper(componentType);

        return mapper.has(entity);
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> ComponentMapper<T> getMapper(Class<T> componentType) {
        if(!mappers.containsKey(componentType)) {
            mappers.put(componentType, ComponentMapper.getFor(componentType));
        }
        return (ComponentMapper<T>) mappers.get(componentType);
    }

}
