package com.game.code.Socket;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.components.*;
import com.game.code.utils.Mappers;
import io.socket.client.Socket;

public class SocketContactScoreSystem extends IteratingSystem {


    private final ObjectMap<Entity, Float> savedHPs = new ObjectMap<>();
    private final Socket socket;

    public SocketContactScoreSystem(Socket socket) {
        super(Family.all(PlayerComponent.class, ContactScoreComponent.class)
                .one(StartsCollusionComponent.class, CollidesComponent.class).get(), 40);

        this.socket = socket;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if(Mappers.has(CollidesComponent.class, entity))
            Mappers.get(CollusionComponent.class, entity).involved.forEach(this::processTarget);
        if(Mappers.has(StartsCollusionComponent.class, entity))
            Mappers.get(StartCollusionComponent.class, entity).involved.forEach(this::addTarget);
    }

    private void addTarget(Entity target) {
        if(!savedHPs.containsKey(target) && Mappers.has(IdComponent.class, target) && Mappers.has(HealthComponent.class, target))
            savedHPs.put(target, Mappers.get(HealthComponent.class, target).currentHP);
    }

    private void processTarget(Entity target) {
        if(!savedHPs.containsKey(target))
            return;

        if(checkHealthOf(target)) {
            socket.emit("hit");
            savedHPs.remove(target);
        } else {
            savedHPs.remove(target);
            return;
        }

        if(isDead(target))
            socket.emit("lethalHit");
    }

    private boolean checkHealthOf(Entity target) {
        float savedHP = this.savedHPs.get(target);
        float currentHP = Mappers.get(HealthComponent.class, target).currentHP;

        return savedHP > currentHP;
    }

    private boolean isDead(Entity target) {
        return Mappers.get(HealthComponent.class, target).currentHP <= 0;
    }
}
