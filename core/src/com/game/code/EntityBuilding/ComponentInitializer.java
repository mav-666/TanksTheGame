package com.game.code.EntityBuilding;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.game.code.EntityBuilding.FieldInitializers.*;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Optional;

public class ComponentInitializer {

    protected final HashMap<Class<?>, FieldInitializer<?, ?>>  initializers = new HashMap<>();

    public ComponentInitializer() {
        this.addInitializer(new FloatInitializer());
        this.addInitializer(new StringInitializer());
        this.addInitializer(new IntegerInitializer());
        this.addInitializer(new BooleanInitializer());
        this.addInitializer(new Vector2Initializer());
    }

    @SuppressWarnings("unchecked")
    public <P> void initField(Component component, String fieldName, P config) throws NoSuchFieldException {
        Field field = component.getClass().getField(fieldName);

        findInitializer(getTypeOf(field)).ifPresentOrElse(
                fieldInitializer ->
                    setField(field, component, ((FieldInitializer<?,P>)fieldInitializer).getInitBy(config)),
                () -> Gdx.app.log("Error","initializer of "+ field.getType().getSimpleName() + " is not found"));
    }

    private void setField(Field field, Component component, Object value) {
        try {
            field.set(component, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
