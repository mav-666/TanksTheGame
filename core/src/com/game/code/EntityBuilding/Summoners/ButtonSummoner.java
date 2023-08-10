package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.components.*;
import com.game.code.utils.Event;

public class ButtonSummoner extends SpriteSummoner {

    Entity button;

    public ButtonSummoner(EntityBuilder entityBuilder, Engine engine, ComponentInitializer componentInitializer) {
        super(entityBuilder, engine, componentInitializer);
    }

    @Override
    public Entity summonBy(Entity summoner) {
        build();

        init(summoner);

        engine.addEntity(button);
        return button;
    }

    protected void build() {
        entityBuilder.build("Button");
        button = entityBuilder.getEntity();
    }

    @Override
    protected void init(Entity summoner) {
        super.init(summoner);

        initTrigger(summoner);
    }

    private void initTrigger(Entity summoner) {
        entityBuilder.build("ButtonTrigger");

        initEvent(summoner);
        entityBuilder.getComponent(ConnectedComponent.class).target = mappers.get(BodyComponent.class, button).body;
        entityBuilder.getComponent(InheritAngleComponent.class).target = button;

        engine.addEntity(entityBuilder.getEntity());
    }

    private void initEvent(Entity summoner) {
        if(mappers.has(ButtonTemplateComponent.class, summoner))
            entityBuilder.getComponent(ContactEventComponent.class).event =
                    mappers.get(ButtonTemplateComponent.class, summoner).activateEvent;
    }

    @Override
    public SummonerType getType() {
        return SummonerType.Button;
    }
}
