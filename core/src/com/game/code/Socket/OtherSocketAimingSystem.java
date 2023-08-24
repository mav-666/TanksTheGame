package com.game.code.Socket;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.components.CanonComponent;
import com.game.code.components.IdComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.DeactivatingSystem;
import com.game.code.utils.Mappers;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

public class OtherSocketAimingSystem extends DeactivatingSystem implements EntityListener {
    public final static Family FAMILY = Family.all(IdComponent.class).one(CanonComponent.class).get();


    private final Socket socket;

    ObjectMap<String, Entity> players = new ObjectMap<>();

    public OtherSocketAimingSystem(Socket socket) {
        super(12);

        this.socket = socket;

        activate();
    }

    private void playerAimedEvent(Object... args) {
        try {
            JSONObject data = (JSONObject) args[0];

            String id = data.getString("id");
            float degAngle = (float) data.getDouble("degAngle");

            if(players.containsKey(id))
                setAngleFor(players.get(id), degAngle);
        } catch (JSONException e) {
            Gdx.app.log("SocketAimError", "Failed reading aimingInput");
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        getEngine().addEntityListener(FAMILY, this);
    }
    
    private void setAngleFor(Entity entity, float degAngle) {
        TransformComponent transformC = Mappers.get(TransformComponent.class, entity);

        transformC.degAngle = degAngle;
    }

    @Override
    public void entityAdded(Entity entity) {
        String id = Mappers.get(IdComponent.class, entity).id;
        players.put(id, entity);
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
    protected void activate() {
        socket.on("playerAimed", this::playerAimedEvent);
    }

    @Override
    protected void deactivate() {
        socket.off("playerAimed");
    }
}
