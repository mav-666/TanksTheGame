package com.game.code.screens.Loading;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import io.socket.client.Ack;
import io.socket.client.Socket;

public interface TaskLoader {

    static Builder create() {
        return new Builder();
    }

    void update() throws LoadingException;

    boolean isDone();

    float getProgress();

    String getName();


    class Builder {

        TaskLoader loadingTask;
        Array<TaskLoader> loadingTasks = new Array<>();

        public Builder add(Task task, String name) {
            loadingTasks.add(new SingleTaskLoader(task, name));

            return this;
        }

        public Builder add(TaskLoader taskLoader) {
            loadingTasks.add(taskLoader);

            return this;
        }
        
        public Builder loadAssets(AssetManager assetManager) {
            loadingTasks.add(new AssetTaskLoader(assetManager));

            return this;
        }

        public Builder loadFromSocket(Socket socket, String eventName, Ack ack, Object... args) {
            loadingTasks.add(new SocketRequestLoader(socket, eventName, ack, args));

            return this;
        }

        public TaskLoader get() {
            if(loadingTasks.isEmpty()) return new SingleTaskLoader(() -> {}, "");
            if(loadingTasks.size > 1) {
                loadingTask = new MultipleTaskLoader(loadingTasks.toArray(TaskLoader.class));
            } else loadingTask = loadingTasks.first();

            return loadingTask;
        }
    }
}
