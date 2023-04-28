package com.game.code.screens;

import com.badlogic.gdx.Screen;

@FunctionalInterface
public interface HistoryFilter {
    boolean filter(Screen screen);
}
