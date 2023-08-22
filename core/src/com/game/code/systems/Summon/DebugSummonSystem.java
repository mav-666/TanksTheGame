package com.game.code.systems.Summon;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.components.BodyComponent;

public class DebugSummonSystem extends EntitySystem {

    enum Entity{
        Box,
        Gasoline,
        Explosion,
    }

    private final EntityBuilder entityBuilder;
    private final Viewport viewport;

    private final Vector2 position = new Vector2();
    private int current = 0;

    public DebugSummonSystem(EntityBuilder entityBuilder, Viewport viewport) {
        this.entityBuilder = entityBuilder;
        this.viewport = viewport;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);


        if(Gdx.input.isKeyJustPressed(Input.Keys.J))
            current = Math.max(0, --current);

        if(Gdx.input.isKeyJustPressed(Input.Keys.L))
            current = Math.min(Entity.values().length-1 , ++current);

        if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            entityBuilder.build(Entity.values()[current].name());
            position.set(Gdx.input.getX(), Gdx.input.getY());
            position.set(viewport.unproject(position));
            entityBuilder.getComponent(BodyComponent.class).body.setTransform(position.x, position.y, 0);
            getEngine().addEntity(entityBuilder.getEntity());
        }
    }
}
