package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.components.*;

public class ButtonSummoner extends SpriteSummoner {

    private final ComponentInitializer componentInitializer;

    Entity button;

    public ButtonSummoner(EntityBuilder entityBuilder, Engine engine, ComponentInitializer componentInitializer) {
        super(entityBuilder, engine, componentInitializer);
        this.componentInitializer = componentInitializer;
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

        initEvent(mappers.get(SummonsNowComponent.class, summoner).entityName);
        entityBuilder.getComponent(ConnectedComponent.class).target = mappers.get(BodyComponent.class, button).body;
        entityBuilder.getComponent(InheritAngleComponent.class).target = button;

        engine.addEntity(entityBuilder.getEntity());
    }

    private void initEvent(String entityName) {
        try {
            componentInitializer.initField(entityBuilder.getComponent(ContactEventComponent.class), "event", entityName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
