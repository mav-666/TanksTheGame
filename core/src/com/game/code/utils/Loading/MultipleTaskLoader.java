package com.game.code.utils.Loading;

import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class MultipleTaskLoader implements LoadingTask {
    protected Array<LoadingTask> allTasks;
    protected Array<LoadingTask> doneTasks;

    protected Iterator<LoadingTask> iterator;

    protected LoadingTask processingTask;

    public MultipleTaskLoader(LoadingTask... loadingTasks) {
        allTasks = new Array<>(loadingTasks);
        doneTasks = new Array<>();

        iterator = allTasks.iterator();
        processingTask = iterator.next();
    }

    @Override
    public void update() throws LoadingException {
        if(processingTask.isDone()) {
            doneTasks.add(processingTask);
            if(iterator.hasNext())
                processingTask = iterator.next();
                return;
        }

        processingTask.update();


    }

    @Override
    public boolean isDone() {
        return doneTasks.size == allTasks.size;
    }

    @Override
    public float getPercent() {
        return ((float) doneTasks.size / (float) allTasks.size) + processingTask.getPercent();
    }

    @Override
    public String getName() {
        return processingTask.getName();
    }
}
