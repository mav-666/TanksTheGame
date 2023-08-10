package com.game.code.Socket;

import com.badlogic.gdx.utils.ObjectMap;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;

import java.net.URI;
import java.util.Collections;

public class ClientSocket {
    private final static String SERVER_URI = "http://localhost:8080/";
//    http://localhost:8080/

    private final URI uri = URI.create(SERVER_URI);
    private final IO.Options options = IO.Options.builder().build();

    private final Socket socket = IO.socket(uri, options);

    private final ObjectMap<String, Socket> namespaces= new ObjectMap<>();



    public Socket getSocket() {
        return socket;
    }

    public Socket getSocket(String nsp) {
        if(!namespaces.containsKey(nsp))
            namespaces.put(nsp, socket.io().socket(nsp));

        return namespaces.get(nsp);
    }

    public void disconnectAll() {
        socket.disconnect();
        namespaces.values().forEach(Socket::disconnect);
    }
}
