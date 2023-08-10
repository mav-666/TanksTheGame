package com.game.code.screens.Loading;

import com.badlogic.gdx.Gdx;
import io.socket.client.Ack;
import io.socket.client.Socket;

public class SocketRequestLoader implements TaskLoader {

    private final Task emitEvent;

    boolean isNotEmitted = true;
    boolean received = false;

    protected SocketRequestLoader(Socket socket, String eventName, Ack ack, Object... args) {
        this.emitEvent = () -> socket.emit(eventName, args,
                (respond) -> {
            received = true;
            ack.call(respond);});
    }

    @Override
    public void update() throws LoadingException {
        if(isNotEmitted) {
            emitEvent.load();
            isNotEmitted = false;
        }
    }

    @Override
    public boolean isDone() {
        return received;
    }

    @Override
    public float getProgress() {
        return received ? 1 : 0;
    }

    @Override
    public String getName() {
        return "socket request";
    }
}
