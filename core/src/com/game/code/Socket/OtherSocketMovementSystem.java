package com.game.code.Socket;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.components.*;
import com.game.code.utils.Mappers;
import io.socket.client.Socket;
import org.apache.commons.lang3.tuple.Triple;
import org.json.JSONException;
import org.json.JSONObject;

public class OtherSocketMovementSystem extends EntitySystem implements EntityListener {
    public final static Family FAMILY = Family.all(IdComponent.class, MobilityComponent.class).get();

    private final Mappers mappers = Mappers.getInstance();

    ObjectMap<String, Entity> players = new ObjectMap<>();
    ObjectMap<String, Triple<Float, Float, Float>> playerTransforms = new ObjectMap<>();
    Array<String> movingPlayers = new Array<>();

    public OtherSocketMovementSystem(Socket socket) {
        super(12);
        socket.on("playerMoved", args -> {
            try {
                JSONObject data = (JSONObject) args[0];

                String id = data.getString("id");
                float x = (float) data.getDouble("x");
                float y = (float) data.getDouble("y");
                float radAngle = (float) Math.toRadians(data.getDouble("degAngle"));
                boolean isMoving = data.getBoolean("isMoving");

                if(players.containsKey(id))
                    playerTransforms.put(id, Triple.of(x, y, radAngle));
                if(isMoving)
                    movingPlayers.add(id);
            } catch (JSONException e) {
                Gdx.app.log("SocketMovementError", "Failed reading movementInput");
            }
        });
    }

    @Override
    public void entityAdded(Entity entity) {
        String id = mappers.get(IdComponent.class).get(entity).id;
        players.put(id, entity);
    }

    @Override
    public void update(float deltaTime) {
        playerTransforms.forEach(entry ->
            setTransformFor(players.get(entry.key), entry.value.getLeft(), entry.value.getMiddle(), entry.value.getRight())
        );
        movingPlayers.forEach(id -> {
            addMoves(players.get(id));
            movingPlayers.removeValue(id, true);
        });
    }

    private void setTransformFor(Entity entity, float x, float y, float radAngle) {
        Body body = mappers.get(BodyComponent.class).get(entity).body;
        body.setTransform(x, y, radAngle);
    }

    private void addMoves(Entity entity) {
        entity.add(getEngine().createComponent(MovesComponent.class));
    }

    @Override
    public void entityRemoved(Entity entity) {
        String id = mappers.get(IdComponent.class).get(entity).id;
        players.remove(id);
    }
}