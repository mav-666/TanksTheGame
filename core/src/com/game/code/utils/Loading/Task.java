package com.game.code.utils.Loading;

@FunctionalInterface
public interface Task {

    void load() throws LoadingException;
}
