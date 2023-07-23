package com.game.code.screens.Loading;

import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class MultipleTaskLoader extends TaskLoader {
    protected Array<TaskLoader> allTasks;
    protected Array<TaskLoader> doneTasks;

    protected Iterator<TaskLoader> iterator;

    protected TaskLoader processingTask;

    protected MultipleTaskLoader(TaskLoader... loadingTasks) {
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

    public void addTask(TaskLoader loadingTask) {
        allTasks.add(loadingTask);
    }

    @Override
    public boolean isDone() {
        return doneTasks.size == allTasks.size;
    }

    @Override
    public float getProgress() {
        return (((float) doneTasks.size) + processingTask.getProgress() / (float) allTasks.size);
    }

    @Override
    public String getName() {
        return processingTask.getName();
    }
}
