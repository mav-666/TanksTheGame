package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.components.SummonsNowComponent;
import com.game.code.components.TextureComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;

public class SpriteSummoner extends EntitySummoner {

    protected final ComponentInitializer componentInitializer;

    public SpriteSummoner(EntityBuilder entityBuilder, Engine engine, ComponentInitializer componentInitializer) {
        super(entityBuilder, engine);

        this.componentInitializer = componentInitializer;
    }

    @Override
    protected void build() {
        entityBuilder.build(SummonerType.Sprite.name());
    }

    @Override
    protected void init(Entity summoner) {
        initTexture(Mappers.get(SummonsNowComponent.class, summoner).entityName);
        super.init(summoner);
    }

    protected void initTexture(String entityName) {
        TextureComponent textureC = Mappers.get(TextureComponent.class, entityBuilder.getEntity());

        try {
            componentInitializer.initField(textureC, "textureRegion", entityName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    protected void initTransformBy(Entity summoner) {
        super.initTransformBy(summoner);

        entityBuilder.getComponent(TransformComponent.class).zIndex++;
    }

    @Override
    public SummonerType getType() {
        return SummonerType.Sprite;
    }
}
