package com.game.code.screens.Loading;

import com.badlogic.gdx.Screen;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

public class ScreenHistory {
    private final ArrayList<Screen> availableScreens;
    private final ArrayList<Predicate<Screen>> filters;

    private int capacity;

    public ScreenHistory(int capacity) {
        this.capacity = capacity;

        availableScreens = new ArrayList<>(capacity);
        filters = new ArrayList<>();
    }

    public Optional<Screen> getScreen(Class<? extends Screen> screenType) {
        return availableScreens.stream().filter(screen -> screen.getClass() == screenType).findFirst();
    }

    public void add(Screen screen) {
        for(Predicate<Screen> filter : filters) {
            if(filter.test(screen)) return;
        }

        if(availableScreens.size() != 0 && availableScreens.get(0) == screen)
            return;

        availableScreens.add(0, screen);

        removeOld();
    }

    private void removeOld() {
        if(availableScreens.size() > capacity) {
            availableScreens.remove(availableScreens.size()-1).dispose();
        }
    }

    public boolean containsScreen(Screen screen) {
        return availableScreens.contains(screen);
    }

    public void addFilter(Predicate<Screen> filter) {
        filters.add(filter);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}

