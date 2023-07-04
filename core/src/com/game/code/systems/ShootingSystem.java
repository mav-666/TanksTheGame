package com.game.code.systems;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.game.code.EntityBuilding.SummoningDirector;
import com.game.code.components.*;
import com.game.code.components.RechargesComponent;
import com.game.code.components.ShootsComponent;
import com.game.code.utils.BodySearcher;
import com.game.code.utils.Mappers;
import com.game.code.utils.TweenUtils.TweenM;
import com.game.code.utils.TweenUtils.Vector2Accessor;

public class ShootingSystem extends IteratingSystem {

    private final Mappers mappers;
    private final BodySearcher bodySearcher;

    private final SummoningDirector summoningDirector;

    public ShootingSystem(SummoningDirector summoningDirector) {
        super(Family.all(ShootsComponent.class, ProjectileTemplateComponent.class, TransformComponent.class).exclude(RechargesComponent.class).get());

        this.summoningDirector = summoningDirector;

        this.bodySearcher = new BodySearcher();
        mappers = Mappers.getInstance();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        entity.remove(ShootsComponent.class);

        Entity projectile = summoningDirector.summonBy(entity);

        addRecoil(entity, projectile);
        addRecharge(entity);
    }

    private void addRecharge(Entity entity) {
        entity.add(getEngine().createComponent(RechargesComponent.class));

        float seconds = mappers.get(RechargingComponent.class).get(entity).seconds;

        Timeline.createSequence()
                .pushPause(seconds)
                .push(Tween.call((type, source) -> entity.remove(RechargesComponent.class)))
                .start(TweenM.getInstance().getManager());
    }

    private void addRecoil(Entity entity, Entity projectile) {
        var optionalBody = bodySearcher.searchForBodyIn(entity);

        if(optionalBody.isEmpty()) return;

        TransformComponent projectileTransform = mappers.get(TransformComponent.class).get(projectile);

        float recoil = calculateRecoil(entity, optionalBody.get().getAngle());

        Vector2 impulse = calculateImpulse(recoil, projectileTransform.degAngle);

        optionalBody.get().applyLinearImpulse(impulse, projectileTransform.position, true);
        addRecoilAnimation(entity);
    }

    private float calculateRecoil(Entity entity, float bodyAngle) {
        float degAngle = mappers.get(TransformComponent.class).get(entity).degAngle;
        float recoil = mappers.get(ProjectileTemplateComponent.class).get(entity).recoil;

        return recoil * calculateRecoilStrength(degAngle, bodyAngle);
    }

    private Vector2 calculateImpulse(float recoil, float angle) {
        return new Vector2(0, -recoil).rotateDeg(angle);
    }

    private float calculateRecoilStrength(float degAngle, float bodyAngle) {
        return (float) Math.abs(Math.cos(Math.toRadians(degAngle) - bodyAngle)) + 0.1f;
    }

    private void addRecoilAnimation(Entity entity) {
        ComponentMapper<TextureComponent> textureM = mappers.get(TextureComponent.class);

        if(!mappers.get(AnimatedRecoilComponent.class).has(entity) || !textureM.has(entity))
                return;

        Vector2 offset = textureM.get(entity).offset;
        float height = textureM.get(entity).height;

        Timeline.createSequence()
                .push(Tween.to(offset, Vector2Accessor.Y, 0.1f).target(-height/3f).ease(TweenEquations.easeOutQuad))
                .push(Tween.to(offset, Vector2Accessor.Y, 0.2f).target(0).ease(TweenEquations.easeInQuad))
                .start(TweenM.getInstance().getManager());
    }
}
