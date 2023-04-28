package com.game.code.utils.Loading;


public interface LoadingTask {

    void update() throws LoadingException;

    boolean isDone();

    float getPercent();

    String getName();
}
