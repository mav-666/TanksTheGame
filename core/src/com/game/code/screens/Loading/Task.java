package com.game.code.screens.Loading;

@FunctionalInterface
public interface Task {

    void load() throws LoadingException;
}
