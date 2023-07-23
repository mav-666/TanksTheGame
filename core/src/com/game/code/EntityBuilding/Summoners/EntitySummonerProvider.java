package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.SummoningDirector;

public class EntitySummonerProvider {

    private final Engine engine;
    private final EntityBuilder entityBuilder;
    private final ComponentInitializer componentInitializer;
    private final Skin skin;
    private final ObjectMap<SummonerType, SummoningDirector> summoners;

    public EntitySummonerProvider(Engine engine, EntityBuilder entityBuilder, ComponentInitializer componentInitializer, Skin skin) {
        this.engine = engine;
        this.entityBuilder = entityBuilder;
        this.componentInitializer = componentInitializer;
        this.skin = skin;

        summoners = new ObjectMap<>();
        summoners.put(SummonerType.Default, new EntitySummoner(entityBuilder, engine));
    }

    public SummoningDirector provide(SummonerType summonerType) {
        if(summoners.containsKey(summonerType))
            return summoners.get(summonerType);
        else
            return createSummoner(summonerType);
    }

    private SummoningDirector createSummoner(SummonerType summonerType) {
        switch(summonerType) {
            case Tank -> summoners.put(summonerType, new TankSummoner(entityBuilder, engine));
            case Projectile -> summoners.put(summonerType, new ProjectileSummoner(entityBuilder, engine));
            case Particle -> summoners.put(summonerType, new ParticleSummoner(entityBuilder, engine, componentInitializer));
            case Sprite -> summoners.put(summonerType, new SpriteSummoner(entityBuilder, engine, componentInitializer));
            case Button -> summoners.put(summonerType, new ButtonSummoner(entityBuilder, engine, componentInitializer));
            case Keyboard -> summoners.put(summonerType, new KeyboardSummoner(entityBuilder, engine, skin));
            default -> {
                return summoners.get(SummonerType.Default);
            }
        }

        return summoners.get(summonerType);
    }

    public EntityBuilder getEntityBuilder() {
        return entityBuilder;
    }
}
