package com.game.code.EntityBuilding.Json;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.JsonValue;
import com.game.code.EntityBuilding.FieldInitializers.Box2dBodyCreation.BodyConfig;
import com.game.code.EntityBuilding.FieldInitializers.Box2dBodyCreation.ShapeFactoryProvider;
import com.game.code.EntityBuilding.FieldInitializers.Box2dBodyCreation.ShapeFactory;


public class BodyConfigJsonFactory extends JsonConfigFactory<BodyConfig> {

    private final ShapeFactoryProvider shapeFactoryProvider;

    public BodyConfigJsonFactory(JsonLoader jsonLoader) {
        super(jsonLoader);

        shapeFactoryProvider = new ShapeFactoryProvider();
    }

    @Override
    protected BodyConfig processJson(JsonValue jsonValue) {
        BodyDef bodyDef= parseBodyDef(jsonValue.get("bodyDef"));

        Array<FixtureDef> fixtureDefs = new Array<>();

        for(JsonValue fixtureDefJson : jsonValue.get("fixtureDefs")) {
            fixtureDefs.add(parseFixtureDef(fixtureDefJson));
        }

        float mass = parseMass(jsonValue);

        return new BodyConfig(bodyDef, mass, fixtureDefs);
    }

    private BodyDef parseBodyDef(JsonValue bodyDefJson) {
        BodyDef bodyDef = new BodyDef();

        if(bodyDefJson.has("fixedRotation")) bodyDef.fixedRotation = bodyDefJson.getBoolean("fixedRotation");
        if(bodyDefJson.has("type")) bodyDef.type = BodyDef.BodyType.values()[(bodyDefJson.getInt("type"))];
        if(bodyDefJson.has("linearDamping")) bodyDef.linearDamping = bodyDefJson.getFloat("linearDamping");
        if(bodyDefJson.has("angularDamping")) bodyDef.angularDamping = bodyDefJson.getFloat("angularDamping");
        if(bodyDefJson.has("bullet")) bodyDef.bullet = bodyDefJson.getBoolean("bullet");

        return bodyDef;
    }

    private FixtureDef parseFixtureDef(JsonValue fixtureJson) {
        FixtureDef fixtureDef = new FixtureDef();

        if(fixtureJson.has("isSensor")) fixtureDef.isSensor = fixtureJson.getBoolean("isSensor");
        if(fixtureJson.has("density")) fixtureDef.density = fixtureJson.getFloat("density");
        if(fixtureJson.has("restitution")) fixtureDef.restitution = fixtureJson.getFloat("restitution");
        if(fixtureJson.has("categoryBits")) fixtureDef.filter.categoryBits = fixtureJson.getShort("categoryBits");
        if(fixtureJson.has("maskBits")) fixtureDef.filter.maskBits = fixtureJson.getShort("maskBits");
        if(fixtureJson.has("shape")) fixtureDef.shape = parseShape(fixtureJson.get("shape"));

        return fixtureDef;
    }

    private Shape parseShape(JsonValue shapeJson) {
        ShapeFactory shapeFactory = shapeFactoryProvider.provideBy(shapeJson.getString("type"));


        return shapeFactory.createShape(parseFloatArray(shapeJson.get("coordinates")));

    }

    private FloatArray parseFloatArray(JsonValue coordinatesJson) {
        FloatArray coordinates = new FloatArray();

        for(JsonValue value : coordinatesJson) {
            coordinates.add(value.asFloat());
        }

        return coordinates;
    }

    private float parseMass(JsonValue bodyJson) {
        if(bodyJson.has("mass"))
            return bodyJson.getFloat("mass");

        return 0f;
    }
}
