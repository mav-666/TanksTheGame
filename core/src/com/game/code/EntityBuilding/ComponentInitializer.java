package com.game.code.EntityBuilding;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.game.code.EntityBuilding.FieldInitializers.FieldInitializer;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Optional;

public class ComponentInitializer {

    protected final HashMap<Class<?>, FieldInitializer<?, ?>> initializers;

    public ComponentInitializer() {
        initializers = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public <P> void initField(Component component, String fieldName, P config) throws NoSuchFieldException {
        Field field = component.getClass().getField(fieldName);

        findInitializer(getTypeOf(field)).ifPresentOrElse(fieldInitializer -> {
            FieldInitializer<?,P> fieldInit = (FieldInitializer<?, P>) fieldInitializer;

            try {
                field.set(component, fieldInit.getInitBy(config));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }, () -> Gdx.app.log("Error","initializer of "+ field.getType().getSimpleName() + " is not found"));
    }

    public Optional<FieldInitializer<?, ?>> findInitializer(Class<?> fieldType) {
        return Optional.ofNullable(initializers.get(fieldType));
    }

    public void addInitializer(FieldInitializer<?, ?> initializer) {
        initializers.put(initializer.getFieldType(), initializer);
    }

    public static Class<?> getTypeOf(Field field) {
        Class<?> type = field.getType();

        if(type.isPrimitive())
            type = ClassUtils.primitiveToWrapper(type);

        return type;
    }
}
