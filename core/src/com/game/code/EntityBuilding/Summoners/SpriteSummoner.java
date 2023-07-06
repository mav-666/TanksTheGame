package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.SummonerType;
import com.game.code.components.SummonsComponent;
import com.game.code.components.TextureComponent;

public class SpriteSummoner extends EntitySummoner {

    protected final ComponentInitializer componentInitializer;

    private Entity summoner;

    SpriteSummoner(EntityBuilder entityBuilder, Engine engine, ComponentInitializer componentInitializer) {
        super(entityBuilder, engine);

        this.componentInitializer = componentInitializer;
    }

    @Override
    public Entity summonBy(Entity summoner) {
        this.summoner = summoner;

        entityBuilder.build(SummonerType.Sprite.name());

        initTexture();
        initTransformBy(summoner);

        engine.addEntity(entityBuilder.getEntity());

        return entityBuilder.getEntity();
    }

    private void initTexture() {
        String entityName = mappers.get(SummonsComponent.class).get(summoner).entityName;

        try {
            componentInitializer.initField(entityBuilder.getComponent(TextureComponent.class), "textureRegion", entityName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
