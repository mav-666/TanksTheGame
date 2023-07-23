package com.game.code.Socket;

import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;

import java.net.URI;
import java.util.Collections;

public class ClientSocket {
    private final static String SERVER_URI = "http://localhost:8080/";
//    http://localhost:8080/
    private final Socket socket;

    public ClientSocket() {
        URI uri = URI.create(SERVER_URI);
        IO.Options options = IO.Options.builder().build();

        socket = IO.socket(uri, options);
    }

    public Socket getSocket() {
        return socket;
    }
}
