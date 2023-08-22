package com.game.code.systems.Box2d;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Box2dDebugSystem extends EntitySystem {

    private World world;
    private Viewport viewport;

    private Box2DDebugRenderer debugRenderer;

    public Box2dDebugSystem(World world, Viewport viewport) {
        super( 41);
        this.world = world;
        this.viewport = viewport;

        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        debugRenderer.render(world, viewport.getCamera().combined);
    }
}
