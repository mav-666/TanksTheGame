package com.game.code.utils.Loading;

public class LoadingException extends Exception {
    private final String taskName;

    public LoadingException(String taskName){
        this.taskName = taskName;
    }

    @Override
    public String toString() {
        return "Failed loading " + taskName + "!";
    }
}
