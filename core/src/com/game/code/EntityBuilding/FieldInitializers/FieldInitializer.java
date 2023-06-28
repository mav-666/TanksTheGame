package com.game.code.EntityBuilding.FieldInitializers;

import java.lang.reflect.ParameterizedType;

public abstract class FieldInitializer<T, P> {

    private final Class<T> fieldType;

    private final Class<P> configType;

    @SuppressWarnings("unchecked")
    public FieldInitializer() {
        fieldType = (Class<T>) ((ParameterizedType)
                this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        configType = ((Class<P>) ((ParameterizedType)
                this.getClass().getGenericSuperclass()).getActualTypeArguments()[1]);

    }

    public abstract T getInitBy(P config);

    public Class<T> getFieldType() {
        return fieldType;
    }

    public Class<P> getConfigType() {
        return configType;
    }
}
