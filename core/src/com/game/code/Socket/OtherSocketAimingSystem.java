package com.game.code.Socket;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.components.CanonComponent;
import com.game.code.components.IdComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

public class OtherSocketAimingSystem extends EntitySystem implements EntityListener {
    public final static Family FAMILY = Family.all(IdComponent.class).one(CanonComponent.class).get();

    private final Mappers mappers = Mappers.getInstance();

    ObjectMap<String, Entity> players = new ObjectMap<>();

    public OtherSocketAimingSystem(Socket socket) {
        super(12);
        socket.on("playerAimed", args -> {
            try {
                JSONObject data = (JSONObject) args[0];

                String id = data.getString("id");
                float degAngle = (float) data.getDouble("degAngle");

                if(players.containsKey(id))
                    setAngleFor(players.get(id), degAngle);
            } catch (JSONException e) {
                Gdx.app.log("SocketAimError", "Failed reading aimingInput");
            }
        });
    }

    private void setAngleFor(Entity entity, float degAngle) {
        TransformComponent transformC = mappers.get(TransformComponent.class, entity);

        transformC.degAngle = degAngle;
    }

    @Override
    public void entityAdded(Entity entity) {
        String id = mappers.get(IdComponent.class, entity).id;
        players.put(id, entity);
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
