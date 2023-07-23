package com.game.code.Socket;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.game.code.components.AimsComponent;
import com.game.code.components.CanonComponent;
import com.game.code.components.PlayerComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;
import io.socket.client.Socket;
import org.json.JSONObject;

import java.util.Collections;

public class SocketAimingSystem extends IntervalIteratingSystem {
    private final static float TIME_STEP = 1/60f;

    private final Mappers mappers = Mappers.getInstance();

    private final Socket socket;

    public SocketAimingSystem(Socket socket) {
        super(Family.all(PlayerComponent.class).one(CanonComponent.class).get(), TIME_STEP, 12);

        this.socket = socket;
    }

    @Override
    protected void processEntity(Entity entity) {
        float angle = mappers.get(TransformComponent.class, entity).degAngle;

        socket.emit("playerAimed", new JSONObject(Collections.singletonMap("degAngle", angle)));
    }
}
