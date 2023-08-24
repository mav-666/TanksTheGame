package com.game.code.Socket;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.components.*;
import com.game.code.utils.Mappers;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketScoreSystem extends EntitySystem implements EntityListener {
    public final static Family FAMILY = Family.all(NameComponent.class).one(IdComponent.class, PlayerComponent.class).get();
    private final Socket socket;

    private final ObjectMap<String, Entity> players = new ObjectMap<>();

    public SocketScoreSystem(Socket socket) {
        super();

        this.socket = socket;

        socket.on("gainsPoints", this::gainsPointsEvent);
    }

    private void gainsPointsEvent(Object... args) {
        try {
            JSONObject data = (JSONObject) args[0];

            String id = data.getString("id");
            int points = data.getInt("points");

            if(players.containsKey(id))
                addPointComponentTo(players.get(id), points);

        } catch (JSONException e) {
            Gdx.app.log("SocketScoreSystem", "Failed reading id");
        }
    }

    private void addPointComponentTo(Entity player, int points) {

        Engine engine = getEngine();
        if(!Mappers.has(GainsPointsComponent.class, player))
            player.add(engine.createComponent(GainsPointsComponent.class));

        Mappers.get(GainsPointsComponent.class, player).points += points;
    }

    @Override
    public void addedToEngine(Engine engine) {
        getEngine().addEntityListener(FAMILY, this);
    }

    @Override
    public void entityAdded(Entity entity) {
        if(Mappers.has(PlayerComponent.class, entity)) {
            players.put("player", entity);
            return;
        }

        players.put(Mappers.get(IdComponent.class, entity).id, entity);
    }

    @Override
    public void entityRemoved(Entity entity) {
    }

    @Override
    public void removedFromEngine(Engine engine) {
        socket.off("gainsPoints");
    }
}
