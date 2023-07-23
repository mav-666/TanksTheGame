package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.game.code.components.ColorComponent;
import com.game.code.components.DeadComponent;
import com.game.code.components.TextureComponent;
import com.game.code.utils.Mappers;



public class FadeAfterDeathSystem extends IteratingSystem {

    private final Mappers mappers = Mappers.getInstance();

    private final Color fadeColor = Color.valueOf("646464");

    public FadeAfterDeathSystem() {
        super(Family.all(DeadComponent.class, TextureComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        mappers.getOrCreate(ColorComponent.class, entity, getEngine()).color.set(fadeColor);
    }
}
