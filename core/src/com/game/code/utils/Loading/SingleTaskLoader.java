package com.game.code.utils.Loading;

public class SingleTaskLoader implements LoadingTask {
    private final String name;
    private final Task task;
    boolean isLoaded = false;

    public SingleTaskLoader(Task task, String name) {
        this.task = task;
        this.name = name;
    }

    @Override
    public void update() throws LoadingException {
        task.load();
        isLoaded = true;
    }

    @Override
    public boolean isDone() {
        return isLoaded;
    }

    @Override
    public float getPercent() {
        return isLoaded? 1f : 0.0f;
    }

    @Override
    public String getName() {
        return name;
    }
}
