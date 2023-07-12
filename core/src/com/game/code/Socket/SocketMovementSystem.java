package com.game.code.Socket;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.game.code.components.MobilityComponent;
import com.game.code.components.MovesComponent;
import com.game.code.components.PlayerComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;
import io.socket.client.Socket;
import org.json.JSONObject;

import java.util.Map;

public class SocketMovementSystem extends IntervalIteratingSystem {
    private final static float TIME_STEP = 1/60f;

    private final Mappers mappers = Mappers.getInstance();

    private final Socket socket;

    public SocketMovementSystem(Socket socket) {
        super(Family.all(PlayerComponent.class, MobilityComponent.class).get(), TIME_STEP, 11);

        this.socket = socket;
    }

    @Override
    protected void processEntity(Entity entity) {
        TransformComponent transformC = mappers.get(TransformComponent.class).get(entity);

        socket.emit("playerMoved", new JSONObject(Map.of(
                "x", transformC.position.x,
                "y", transformC.position.y,
                "degAngle", transformC.degAngle,
                "isMoving", mappers.get(MovesComponent.class).has(entity))));
    }
}
