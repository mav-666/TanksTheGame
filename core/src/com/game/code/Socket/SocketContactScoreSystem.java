package com.game.code.Socket;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.components.*;
import com.game.code.utils.Mappers;
import io.socket.client.Socket;

public class SocketContactScoreSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();

    private final ObjectMap<Entity, Float> savedHPs = new ObjectMap<>();
    private final Socket socket;

    public SocketContactScoreSystem(Socket socket) {
        super(Family.all(PlayerComponent.class, ContactScoreComponent.class)
                .one(StartsCollusionComponent.class, CollidesComponent.class).get(), 40);

        this.socket = socket;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if(mappers.has(CollidesComponent.class, entity))
            mappers.get(CollusionComponent.class, entity).involved.forEach(this::processTarget);
        if(mappers.has(StartsCollusionComponent.class, entity))
            mappers.get(StartCollusionComponent.class, entity).involved.forEach(this::addTarget);
    }

    private void addTarget(Entity target) {
        if(!savedHPs.containsKey(target) && mappers.has(IdComponent.class, target) && mappers.has(HealthComponent.class, target))
            savedHPs.put(target, mappers.get(HealthComponent.class, target).currentHP);
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
        float currentHP = mappers.get(HealthComponent.class, target).currentHP;

        return savedHP > currentHP;
    }

    private boolean isDead(Entity target) {
        return mappers.get(HealthComponent.class, target).currentHP <= 0;
    }
}
