package com.game.code.EntityBuilding.Json;

import com.badlogic.gdx.utils.JsonValue;
import com.game.code.EntityBuilding.FieldInitializers.TankConfig.*;

public class TankConfigJsonFactory extends JsonConfigFactory<TankConfig> {

    public TankConfigJsonFactory(JsonLoader jsonLoader) {
        super(jsonLoader);
    }

    @Override
    protected TankConfig parseJson(JsonValue jsonValue) {
        CabConfig cabConfig = jsonValue.has("cab") ? parseCab(jsonValue.get("cab")) : new CabConfig();
        HeadConfig headConfig = jsonValue.has("head") ? parseHead(jsonValue.get("head")) : new HeadConfig();
        BarrelConfig barrelConfig = jsonValue.has("barrel") ? parseBarrel(jsonValue.get("barrel")) : new BarrelConfig();

        return new TankConfig(cabConfig, headConfig, barrelConfig);
    }

    private CabConfig parseCab(JsonValue jsonValue) {
        String cabName = jsonValue.getString("cabName");
        float movementSpeed = jsonValue.getFloat("movementSpeed");
        float agility = jsonValue.getFloat("agility");
        float HP = jsonValue.getFloat("HP");

        return new CabConfig(cabName, movementSpeed, agility, HP);
    }

    private HeadConfig parseHead(JsonValue jsonValue) {
        String headName = jsonValue.getString("headName");
        float rotationSpeed = jsonValue.getFloat("rotationSpeed");

        return new HeadConfig(headName, rotationSpeed);
    }

    private BarrelConfig parseBarrel(JsonValue jsonValue) {
        String barrelName = jsonValue.getString("barrelName");
        float rechargeSeconds = jsonValue.getFloat("rechargeSeconds");
        ProjectileConfig projectileConfig = jsonValue.has("projectile") ? parseProjectile(jsonValue.get("projectile")) : new ProjectileConfig();

        return new BarrelConfig(barrelName, rechargeSeconds, projectileConfig);
    }

    private ProjectileConfig parseProjectile(JsonValue jsonValue) {
        String projectileName = jsonValue.getString("projectileName");
        float radius = jsonValue.getFloat("radius");
        float recoil = jsonValue.getFloat("recoil");
        float speed = jsonValue.getFloat("speed");
        float contactDamage = jsonValue.getFloat("contactDamage");

        return new ProjectileConfig(projectileName, radius, recoil, speed, contactDamage);
    }
}
