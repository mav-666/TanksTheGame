package com.game.code.systems.Box2d.Contact;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.*;
import com.game.code.utils.Mappers;

public class ContactDamageSystem extends IteratingSystem {
    public ContactDamageSystem() {
        super(Family.all(ContactDamageComponent.class, StartsCollusionComponent.class).get());


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        StartCollusionComponent collusion = Mappers.get(StartCollusionComponent.class, entity);

        float damage = Mappers.get(ContactDamageComponent.class, entity).damage;

        collusion.involved.forEach(involved -> dealDamageTo(involved, damage));
    }

    private void dealDamageTo(Entity entity, float damage) {
        ComponentMapper<TakesDamageComponent> takesDamageM = Mappers.getMapper(TakesDamageComponent.class);
        if(!takesDamageM.has(entity)) {
            TakesDamageComponent takesDamage = getEngine().createComponent(TakesDamageComponent.class);
            entity.add(takesDamage);
        }

        takesDamageM.get(entity).damage += damage;
    }
}
