package com.game.code.Socket;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.components.BodyComponent;
import com.game.code.components.IdComponent;
import com.game.code.components.MobilityComponent;
import com.game.code.components.MovesComponent;
import com.game.code.utils.DeactivatingSystem;
import com.game.code.utils.Mappers;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

public class OtherSocketMovementSystem extends DeactivatingSystem implements EntityListener {
    public final static Family FAMILY = Family.all(IdComponent.class, MobilityComponent.class).get();

    private final Socket socket;

    private final ObjectMap<String, Entity> players = new ObjectMap<>();
    private final ObjectMap<String, TransformData> playerTransforms = new ObjectMap<>();
    private final Array<String> movingPlayers = new Array<>();

    public OtherSocketMovementSystem(Socket socket) {
        super(12);

        this.socket = socket;

        activate();
    }

    private void playerMovedEvent(Object... args) {
        try {
            JSONObject data = (JSONObject) args[0];

            String id = data.getString("id");
            float x = (float) data.getDouble("x");
            float y = (float) data.getDouble("y");
            float radAngle = (float) Math.toRadians(data.getDouble("degAngle"));
            boolean isMoving = data.getBoolean("isMoving");

            if(players.containsKey(id))
                playerTransforms.put(id, TransformData.create().x(x).y(y).radAngle(radAngle).build());
            if(isMoving)
                movingPlayers.add(id);
        } catch (JSONException e) {
            Gdx.app.log("SocketMovementError", "Failed reading movementInput");
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        getEngine().addEntityListener(FAMILY, this);
    }

    @Override
    public void entityAdded(Entity entity) {
        String id = Mappers.get(IdComponent.class, entity).id;
        players.put(id, entity);
    }

    @Override
    public void update(float deltaTime) {
        playerTransforms.forEach(entry ->
            setTransformFor(players.get(entry.key), entry.value.x, entry.value.y, entry.value.radAngle)
        );
        movingPlayers.forEach(id -> {
            addMoves(players.get(id));
            movingPlayers.removeValue(id, true);
        });
    }

    private void setTransformFor(Entity entity, float x, float y, float radAngle) {
        Body body = Mappers.get(BodyComponent.class, entity).body;
        body.setTransform(x, y, radAngle);
    }

    private void addMoves(Entity entity) {
        entity.add(getEngine().createComponent(MovesComponent.class));
    }

    @Override
    public void entityRemoved(Entity entity) {
        String id = Mappers.get(IdComponent.class, entity).id;
        movingPlayers.removeValue(id, true);
        playerTransforms.remove(id);
        players.remove(id);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        deactivate();
    }


    @Override
    protected void activate() {
        socket.on("playerMoved", this::playerMovedEvent);
    }

    @Override
    protected void deactivate() {
        socket.off("playerMoved");
    }

    private static class TransformData {
        public float x;
        public float y;
        public float radAngle;

        private TransformData(Builder builder) {
            x = builder.x;
            y = builder.y;
            radAngle = builder.radAngle;
        }

        public static Builder create() {
            return new Builder();
        }

        public static final class Builder {
            private float x;
            private float y;
            private float radAngle;

            private Builder() {
            }

            public Builder x(float val) {
                x = val;
                return this;
            }

            public Builder y(float val) {
                y = val;
                return this;
            }

            public Builder radAngle(float val) {
                radAngle = val;
                return this;
            }

            public TransformData build() {
                return new TransformData(this);
            }
        }
    }
}