package com.game.code.UI.screens.Loading;

@FunctionalInterface
public interface Task {

    void load() throws LoadingException;
}
