package com.game.code.Socket;

import com.game.code.utils.Event;

public record OnlineGameData(String id, String name, State state, Event event) {

    public enum State {
        WAITING,
        RUNNING,
        CLOSED,
    }
}


