package com.game.code.utils;

import com.badlogic.ashley.core.EntitySystem;

public abstract class DeactivatingSystem extends EntitySystem {

    public DeactivatingSystem() {}

    public DeactivatingSystem(int priority) {
        super(priority);
    }

    @Override
    public void setProcessing(boolean processing) {
        if(checkProcessing() == processing) return;

        super.setProcessing(processing);

        if(processing)
            activate();
        else
            deactivate();
    }

    protected abstract void activate();

    protected abstract void deactivate();
}
