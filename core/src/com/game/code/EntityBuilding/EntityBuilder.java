package com.game.code.EntityBuilding;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.game.code.EntityBuilding.FieldInitializers.FieldInitializer;
import com.game.code.utils.Mappers;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Optional;

public abstract class EntityBuilder {
    private final Mappers mappers;

    protected final Engine engine;

    protected Entity entity;

    protected final HashMap<Class<?>, FieldInitializer<?, ?>> initializers;

    public EntityBuilder(Engine engine) {
        this.engine = engine;
        initializers = new HashMap<>();

        mappers = Mappers.getInstance();
    }

    public abstract void build(String entityName);

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

    protected Class<?> getTypeOf(Field field) {
        Class<?> type = field.getType();

        if(type.isPrimitive())
            type = ClassUtils.primitiveToWrapper(type);

        return type;
    }

    public Entity getEntity() {
        return entity;
    }
}
