package com.game.code.EntityBuilding.FieldInitializers;

import java.lang.reflect.ParameterizedType;

public abstract class ArrayFieldInitializer<T,P> extends FieldInitializer<T, P[]> {

    private final Class<P> elementType;

    @SuppressWarnings("unchecked")
    ArrayFieldInitializer() {
        elementType = ((Class<P>) ((ParameterizedType)
                this.getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<P[]> getConfigType() {
        return (Class<P[]>) super.getConfigType().arrayType();
    }

    public Class<P> getElementType() {
        return elementType;
    }
}
