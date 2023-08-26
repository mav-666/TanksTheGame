package com.game.code.systems.Box2d.Contact;

import com.badlogic.ashley.core.*;
import com.game.code.components.*;
import com.game.code.utils.Mappers;

public class ContactDamageListener implements EntityListener {
    public static final Family FAMILY = Family.all(ContactDamageComponent.class, StartsCollusionComponent.class).get();

    private final Engine engine;

    public ContactDamageListener(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void entityAdded(Entity entity) {
        StartCollusionComponent collusion = Mappers.get(StartCollusionComponent.class, entity);

        float damage = Mappers.get(ContactDamageComponent.class, entity).damage;

        collusion.involved.forEach(involved -> dealDamageTo(involved, damage));
    }

    private void dealDamageTo(Entity entity, float damage) {
        if(Mappers.has(IdComponent.class, entity))
            return;

        ComponentMapper<TakesDamageComponent> takesDamageM = Mappers.getMapper(TakesDamageComponent.class);

        if(takesDamageM.has(entity)) {
            takesDamageM.get(entity).damage += damage;
            return;
        }

        TakesDamageComponent takesDamage = engine.createComponent(TakesDamageComponent.class);
        takesDamage.damage += damage;
        entity.add(takesDamage);
    }


    @Override
    public void entityRemoved(Entity entity) {

    }
}


