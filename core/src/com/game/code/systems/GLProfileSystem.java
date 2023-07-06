package com.game.code.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.profiling.GLProfiler;

public class GLProfileSystem extends EntitySystem {

    private final GLProfiler profiler;

    public GLProfileSystem() {
        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Gdx.app.log("Profiler - Textures", String.valueOf(profiler.getTextureBindings()));

        profiler.reset();
    }
}
