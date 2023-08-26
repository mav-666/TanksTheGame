package com.game.code.Socket;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.components.DeadComponent;
import com.game.code.components.HealthComponent;
import com.game.code.components.IdComponent;
import com.game.code.components.TakesDamageComponent;
import com.game.code.utils.DeactivatingSystem;
import com.game.code.utils.Mappers;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

public class OtherSocketDamagingSystem extends DeactivatingSystem implements EntityListener {
    public final static Family FAMILY = Family.all(IdComponent.class, HealthComponent.class).get();

    private final Socket socket;

    private final ObjectMap<String, Entity> players = new ObjectMap<>();

    public OtherSocketDamagingSystem(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void entityAdded(Entity entity) {
        String id = Mappers.get(IdComponent.class, entity).id;
        players.put(id, entity);
    }

    @Override
    public void addedToEngine(Engine engine) {
        engine.addEntityListener(FAMILY, this);
        activate();
    }

    @Override
    protected void activate() {
        socket.on("takesDamage", this::takesDamageEvent);
    }

    private void takesDamageEvent(Object... args) {
        try{
            JSONObject data = ((JSONObject) args[0]);

            String id = data.getString("id");
            float damage = (float) data.getDouble("damage");

            if(players.containsKey(id))
                dealDamageTo(players.get(id), damage);

        } catch (JSONException e) {
            Gdx.app.log("SocketDamagingError", "Failed reading takingDamage");
        }
    }

    private void dealDamageTo(Entity entity, float damage) {
        ComponentMapper<TakesDamageComponent> takesDamageM = Mappers.getMapper(TakesDamageComponent.class);

        if(takesDamageM.has(entity)) {
            takesDamageM.get(entity).damage += damage;
            return;
        }

        TakesDamageComponent takesDamage = getEngine().createComponent(TakesDamageComponent.class);
        takesDamage.damage += damage;
        entity.add(takesDamage);
    }

    @Override
    public void entityRemoved(Entity entity) {
        String id = Mappers.get(IdComponent.class, entity).id;
        players.remove(id);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        deactivate();
    }

    @Override
    protected void deactivate() {
        socket.off("takesDamage");
    }


}
