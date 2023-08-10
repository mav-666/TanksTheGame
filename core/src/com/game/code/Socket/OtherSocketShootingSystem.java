package com.game.code.Socket;

import com.badlogic.ashley.core.*;
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

        socket.on("playerShot", this::playerShotEvent);
    }

    private void playerShotEvent(Object... args) {
        try {
            JSONObject data = (JSONObject) args[0];

            String id = data.getString("id");

            if(players.containsKey(id))
                addShoots(players.get(id));
        } catch (JSONException e) {
            Gdx.app.log("OtherSocketShootingSystem", "Failed reading shootInput");
        }
    }
    
    @Override
    public void addedToEngine(Engine engine) {
        getEngine().addEntityListener(FAMILY, this);
    }

    private void addShoots(Entity entity) {
        entity.add(getEngine().createComponent(ShootsComponent.class));
    }

    @Override
    public void entityAdded(Entity entity) {
        String id = mappers.get(IdComponent.class, entity).id;
        players.put(id, entity);
    }

    @Override
    public void entityRemoved(Entity entity) {
        String id = mappers.get(IdComponent.class, entity).id;
        players.remove(id);
    }
}