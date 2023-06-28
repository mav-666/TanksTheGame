package com.game.code.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.code.components.CollidesComponent;
import com.game.code.components.CollusionComponent;
import com.game.code.components.ContactDamageComponent;
import com.game.code.components.TakesDamageComponent;
import com.game.code.utils.Mappers;

public class ContactDamageSystem extends IteratingSystem {

    private final Mappers mappers;

    public ContactDamageSystem() {
        super(Family.all(ContactDamageComponent.class, CollusionComponent.class, CollidesComponent.class).get());

        mappers = Mappers.getInstance();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollusionComponent collusion = mappers.get(CollusionComponent.class).get(entity);

        float damage = mappers.get(ContactDamageComponent.class).get(entity).damage;

        collusion.involved.forEach(involved -> dealDamageTo(involved, damage));
    }

    private void dealDamageTo(Entity entity, float damage) {
        ComponentMapper<TakesDamageComponent> takesDamageM = mappers.get(TakesDamageComponent.class);
        if(!takesDamageM.has(entity)) {
            TakesDamageComponent takesDamage = getEngine().createComponent(TakesDamageComponent.class);
            entity.add(takesDamage);
        }

        takesDamageM.get(entity).damage += damage;
    }
}
