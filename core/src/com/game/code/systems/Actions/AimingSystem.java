package com.game.code.systems.Actions;
;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.game.code.components.*;
import com.game.code.components.AimsComponent;
import com.game.code.utils.Mappers;

public class AimingSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();

    public AimingSystem() {
        super(Family.all(TransformComponent.class, AimsComponent.class, CanonComponent.class).get(), 11);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector2 aimPoint = mappers.get(AimsComponent.class, entity).target;

        TransformComponent transform = mappers.get(TransformComponent.class, entity);

        CanonComponent canon = mappers.get(CanonComponent.class, entity);

        Vector2 originToAim = aimPoint.cpy()
                .sub(transform.position.x, transform.position.y).nor();

        Vector2 barrel =  transform.position.cpy().add(0,1)
                .sub(transform.position.x, transform.position.y).nor().rotateDeg(transform.degAngle);

        if(Math.abs(originToAim.angleDeg(barrel)) < 5) return;

        transform.degAngle += Math.toDegrees(canon.rotationSpeed/barrel.len() * deltaTime * (originToAim.angleDeg(barrel)>180? -1: 1));

        entity.remove(AimsComponent.class);
    }
}
