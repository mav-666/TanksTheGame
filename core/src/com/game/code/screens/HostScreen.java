package com.game.code.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.game.code.Application;
import com.game.code.EntityBuilding.Summoners.SettingSummoner;
import com.game.code.UI.Meter;
import com.game.code.components.SummonsNowComponent;
import com.game.code.components.WidgetComponent;
import com.game.code.screens.Loading.TaskLoader;
import com.game.code.utils.Mappers;
import io.socket.client.Ack;
import io.socket.client.Socket;
import org.json.JSONObject;

import java.util.Map;

public class HostScreen extends GameLobbyScreen {

    public HostScreen(Application app) {
        super(app);
    }

    @Override
    public TaskLoader getLoadingTask() {
        return TaskLoader.create()
                .add(super.getLoadingTask())
                .add(this::createGrid, "grid")
                .get();
    }

    @Override
    protected void initSettingSummoner() {
        Socket host = app.getSocket("/host");

        entitySummonerProvider.add(new SettingSummoner(entityBuilder, engine, app.skin, entityCreator) {

            @Override
            public Entity summonBy(Entity summoner) {
                String fillingName = Mappers.get(SummonsNowComponent.class, summoner).entityName;
                Entity meterE =  super.summonBy(summoner);

                Meter meter = ((Meter) Mappers.get(WidgetComponent.class, meterE).widget);

                meter.getSignal().add(((signal, value) ->
                        host.emit("settingChanged", new JSONObject(Map.of("name", fillingName, "value", value)))));

                return meterE;
            }
        });

    }

    @Override
    protected void initSocket() {
        super.initSocket();

        Socket host = app.getSocket("/host");

        host.on(Socket.EVENT_CONNECT, args -> host.emit("hostGame",
                new JSONObject(Map.of("gameName", Gdx.app.getPreferences("prefs").getString("playerName")))
                ,(Ack) (argsAck) -> {
                    Socket room = app.getSocket("/inRoom");
                    roomId = host.id();
                    room.on(Socket.EVENT_CONNECT, argsR -> room.emit("joinRoom",  new JSONObject(Map.of("roomId", host.id()))));
                    room.connect();
                }));
        host.connect();
    }

    @Override
    protected void backButtonEvent() {
        Socket host = app.getSocket("/host");
        host.disconnect();
        host.off();

        super.backButtonEvent();
    }
}
