package com.game.code.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.game.code.Application;
import com.game.code.EntityBuilding.Summoners.SettingSummoner;
import com.game.code.Socket.SocketGameSettingsParser;
import com.game.code.Socket.SocketSettingsRefresher;
import com.game.code.UI.Meter;
import com.game.code.components.SettingTemplateComponent;
import com.game.code.components.SummonsNowComponent;
import com.game.code.components.WidgetComponent;
import com.game.code.screens.Loading.TaskLoader;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class JoinScreen extends GameLobbyScreen {

    public JoinScreen(Application app, String roomId) {
        super(app);

        this.roomId = roomId;
    }

    @Override
    public TaskLoader getLoadingTask() {
        return TaskLoader.create()
                .add(super.getLoadingTask())
                .loadFromSocket(app.getSocket("/inRoom"), "getGameSettings",
                    this::initGameSettings,
                    new JSONObject(Map.of("roomId", roomId)))
                .add(this::createGrid, "grid")
                .get();
    }

    private void initGameSettings(Object... args) {
        try{
            JSONObject data = (JSONObject) args[0];
            JSONObject gameSettingsData = data.getJSONObject("gameSettings");

            new SocketGameSettingsParser(gameSettings).parseGameSettingsData(gameSettingsData);

        } catch (JSONException e) {
            Gdx.app.log("InitGameSettingsError", "Failed reading gameSettingsData");
        }
    }

    @Override
    protected void initSettingSummoner() {
        Socket socket = app.getSocket("/inRoom");
        SocketSettingsRefresher settingsRefresher = new SocketSettingsRefresher(socket);

        entitySummonerProvider.add(new SettingSummoner(entityBuilder, engine, app.skin, entityCreator) {

            @Override
            public Entity summonBy(Entity summoner) {
                String fillingName = mappers.get(SummonsNowComponent.class, summoner).entityName;
                Entity meterE =  super.summonBy(summoner);

                Meter meter = ((Meter) mappers.get(WidgetComponent.class, meterE).widget);

                settingsRefresher.addSetting(fillingName, meter);

                return meterE;
            }
        });

        entityCreator.getCreationSettings(SettingTemplateComponent.class).isDisabled = true;
    }

    @Override
    protected void initSocket() {
        super.initSocket();

        Socket room = app.getSocket("/inRoom");
        room.on(Socket.EVENT_CONNECT, args -> room.emit("joinRoom",  new JSONObject(Map.of("roomId", roomId))));
        room.connect();
    }
}
