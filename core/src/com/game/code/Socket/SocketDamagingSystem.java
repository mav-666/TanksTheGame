package com.game.code.Socket;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.game.code.components.PlayerComponent;
import com.game.code.components.TakesDamageComponent;
import com.game.code.utils.Mappers;
import io.socket.client.Socket;
import org.json.JSONObject;

import java.util.Map;

public class SocketDamagingSystem implements EntityListener {
    public static final Family FAMILY = Family.all(PlayerComponent.class, TakesDamageComponent.class).get();

    private final Socket socket;

    public SocketDamagingSystem(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void entityAdded(Entity entity) {
        float damage = Mappers.get(TakesDamageComponent.class, entity).damage;

        socket.emit("takeDamage", new JSONObject(Map.of("damage", damage)));
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
