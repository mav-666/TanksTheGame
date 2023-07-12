package com.game.code.Socket;

import com.game.code.systems.ShootingInputSystem;
import io.socket.client.Socket;

public class SocketShootingInputSystem extends ShootingInputSystem {

    private final Socket socket;

    public SocketShootingInputSystem(Socket socket) {
        super();
        this.socket = socket;
    }

    @Override
    protected void updateAfterCheck() {
        socket.emit("playerShot");
        super.updateAfterCheck();
    }
}
