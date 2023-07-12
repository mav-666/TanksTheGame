package com.game.code.Socket;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.components.IdComponent;
import com.game.code.components.ProjectileTemplateComponent;
import com.game.code.components.ShootsComponent;
import com.game.code.utils.Mappers;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

public class OtherSocketShootingSystem extends EntitySystem implements EntityListener {
    public final static Family FAMILY = Family.all(IdComponent.class, ProjectileTemplateComponent.class).get();

    private final Mappers mappers = Mappers.getInstance();

    ObjectMap<String, Entity> players = new ObjectMap<>();

    public OtherSocketShootingSystem(Socket socket) {
        super(12);
        socket.on("playerShot", args -> {
            try {
                JSONObject data = (JSONObject) args[0];

                String id = data.getString("id");

                if(players.containsKey(id));
                    addShoots(players.get(id));
            } catch (JSONException e) {
                Gdx.app.log("SocketMovementError", "Failed reading movementInput");
            }
        });
    }

    private void addShoots(Entity entity) {
        entity.add(getEngine().createComponent(ShootsComponent.class));
    }

    @Override
    public void entityAdded(Entity entity) {
        String id = mappers.get(IdComponent.class).get(entity).id;
        players.put(id, entity);
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}