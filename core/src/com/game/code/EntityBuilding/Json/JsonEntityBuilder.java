package com.game.code.EntityBuilding.Json;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.FieldInitializers.ArrayFieldInitializer;
import com.game.code.EntityBuilding.FieldInitializers.FieldInitializer;

import java.lang.reflect.Field;

public class JsonEntityBuilder extends EntityBuilder {
    private final Json json;
    private final JsonLoader jsonLoader;

    private final ComponentInitializer componentInitializer;

    public JsonEntityBuilder(Engine engine, ComponentInitializer componentInitializer, JsonLoader jsonLoader) {
        super(engine);
        this.json = new Json();

        this.jsonLoader = jsonLoader;
        this.componentInitializer = componentInitializer;
    }

    @Override
    public void build(String entityName) {
        super.build(entityName);

        var json = jsonLoader.findJson(entityName);

        json.ifPresent(this::createByJson);
    }

    private void createByJson(JsonValue entityJson) {
        entityJson.forEach(this::parseComponentJson);
    }

    private void parseComponentJson(JsonValue componentJson) {
        Component component = getComponent(createComponentClass(componentJson.getString("name")));

        if(componentJson.has("init"))
            initComponent(component, componentJson.get("init"));
    }

    private Class<? extends Component> createComponentClass(String componentName) {
        Class<? extends Component> component;
        try {
            String className = "com.game.code.components." + componentName;
            component = Class.forName(className).asSubclass(Component.class);
        } catch (ClassNotFoundException e) {
            Gdx.app.log("Error", "json reading failed due to non existing component " + componentName);
            return null;
        }

        return component;
    }


    private void initComponent(Component component, JsonValue jsonComponent) {
        jsonComponent.forEach((jsonField) -> {
            try {
                Field field = component.getClass().getField(jsonField.name);

                var fieldInit = componentInitializer.findInitializer(componentInitializer.getTypeOf(field));
                if(fieldInit.isEmpty()) return;

                componentInitializer.initField(component, jsonField.name, getConfigFrom(jsonField, fieldInit.get()));
            } catch (NoSuchFieldException e) {
                Gdx.app.log("Error", "json reading failed due to inappropriate field");
                e.printStackTrace();
            }
        });
    }

    private <P> P getConfigFrom(JsonValue jsonField, FieldInitializer<?,P> fieldInit) {
        Class<P> configType = fieldInit.getConfigType();

        if(configType.isArray()) {
            Class<?> elementType = ((ArrayFieldInitializer<?,?>) fieldInit).getElementType();

            return json.fromJson(configType, elementType, jsonField.toJson(JsonWriter.OutputType.minimal));
        } else
            return json.fromJson(configType, jsonField.toJson(JsonWriter.OutputType.minimal));
    }
}
