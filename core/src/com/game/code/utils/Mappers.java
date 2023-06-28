package com.game.code.utils;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

import java.util.HashMap;

public class Mappers {

    HashMap<Class<? extends Component>, ComponentMapper<? extends Component>> mappers;

    private static Mappers instance;

    private Mappers() {
        mappers = new HashMap<>();
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
