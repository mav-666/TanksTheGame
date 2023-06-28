package com.game.code.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.game.code.EntityBuilding.Projectiles;

public class ProjectileTemplateComponent implements Component, Pool.Poolable {
    public String entityName = Projectiles.Bullet.name();

    public float speed = 0;
    public float recoil = 0;
    public float contactDamage = 0;
    public float radius = 0;

    public Vector2 shootingPoint = new Vector2();

    @Override
    public void reset() {
        entityName = Projectiles.Bullet.name();
        speed = 0;
        recoil = 0;
        contactDamage = 0;
        radius = 0;
        shootingPoint.set(0,0);
    }
}
