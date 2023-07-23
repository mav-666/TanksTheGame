package com.game.code.screens.Loading;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;

public abstract class TaskLoader {

    private static Builder builder = new Builder();

    public static Builder create() {
        return builder.reset();
    }


    public abstract void update() throws LoadingException;

    public abstract boolean isDone();

    public abstract float getProgress();

    public abstract String getName();


    public static class Builder {

        TaskLoader loadingTask;
        Array<TaskLoader> loadingTasks = new Array<>();

        public Builder add(Task task, String name) {
            loadingTasks.add(new SingleTaskLoader(task, name));

            return this;
        }
        
        public Builder loadAssets(AssetManager assetManager) {
            loadingTasks.add(new AssetTaskLoader(assetManager));

            return this;
        }

        public TaskLoader get() {
            if(loadingTasks.isEmpty()) return new SingleTaskLoader(() -> {}, "");
            if(loadingTasks.size > 1) {
                loadingTask = new MultipleTaskLoader(loadingTasks.toArray(TaskLoader.class));
            } else loadingTask = loadingTasks.first();

            return loadingTask;
        }

        public Builder reset() {
            loadingTasks.clear();

            return this;
        }
    }
}
