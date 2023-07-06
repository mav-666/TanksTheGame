package com.game.code.utils;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.HashMap;

public class Mappers {

    ObjectMap<Class<? extends Component>, ComponentMapper<? extends Component>> mappers;

    private static Mappers instance;

    private Mappers() {
        mappers = new ObjectMap<>();
    }

    public static Mappers getInstance() {
        if(instance == null) {
            instance = new Mappers();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> ComponentMapper<T> get(Class<T> componentType) {
        if(!mappers.containsKey(componentType)) {
            mappers.put(componentType, ComponentMapper.getFor(componentType));
        }
        return (ComponentMapper<T>) mappers.get(componentType);
    }
}
