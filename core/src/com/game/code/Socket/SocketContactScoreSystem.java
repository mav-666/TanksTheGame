package com.game.code.Socket;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.components.*;
import com.game.code.utils.Mappers;
import io.socket.client.Socket;

public class SocketContactScoreSystem extends EntitySystem {
    public static final Family FAMILY = Family.all(PlayerComponent.class, ContactScoreComponent.class)
            .one(StartsCollusionComponent.class, CollidesComponent.class).get();

    private final ObjectMap<Entity, Float> savedHPs = new ObjectMap<>();
    private final Socket socket;

    public SocketContactScoreSystem(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void addedToEngine(Engine engine) {
        engine.addEntityListener(Family.all(PlayerComponent.class, ContactScoreComponent.class, StartsCollusionComponent.class).get(),
                new EntityListener() {
                    @Override
                    public void entityAdded(Entity entity) {
                        Mappers.get(StartCollusionComponent.class, entity).involved.forEach(this::addTarget);
                    }

                    private void addTarget(Entity target) {
                        if(!savedHPs.containsKey(target) && Mappers.has(IdComponent.class, target) && Mappers.has(HealthComponent.class, target))
                            savedHPs.put(target, Mappers.get(HealthComponent.class, target).currentHP);
                    }

                    @Override
                    public void entityRemoved(Entity entity) {

                    }
                });

        engine.addEntityListener(Family.all(IdComponent.class, TakesDamageComponent.class).get(),
                new EntityListener() {
                    @Override
                    public void entityAdded(Entity entity) {
                        processTarget(entity);
                    }

                    private void processTarget(Entity target) {
                        if(!savedHPs.containsKey(target))
                            return;

                        if(isDead(target))
                            socket.emit("lethalHit");
                        else
                            socket.emit("hit");


                        savedHPs.remove(target);
                    }


                    private boolean isDead(Entity target) {
                        float damage = Mappers.get(TakesDamageComponent.class, target).damage;
                        Gdx.app.log("afa", Mappers.get(HealthComponent.class, target).currentHP + " " + damage +  "");
                        return Mappers.get(HealthComponent.class, target).currentHP - damage <= 0;
                    }

                    @Override
                    public void entityRemoved(Entity entity) {

                    }

                });
    }
}
