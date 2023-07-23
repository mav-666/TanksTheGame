package com.game.code.EntityBuilding.FieldInitializers;

import com.game.code.EntityBuilding.Summoners.EventProvider;
import com.game.code.utils.Event;

public class EventFieldInitializer extends FieldInitializer<Event, String> {

    private final EventProvider eventProvider;

    public EventFieldInitializer(EventProvider eventProvider) {
        this.eventProvider = eventProvider;
    }

    @Override
    public Event getInitBy(String config) {
        return eventProvider.provide(config);
    }


}
