package com.game.code.EntityBuilding.Summoners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.utils.Event;

public class EventProvider {

    ObjectMap<String, Event> events = new ObjectMap<>();

    public Event provide(String eventName) {
        Event event = events.get(eventName);

        if(event == null) {
            event = () -> {};
            Gdx.app.log("Error", "No such event: " + eventName);
        }

        return event;
    }

    public void add(String eventName, Event event) {
        events.put(eventName, event);
    }
}
