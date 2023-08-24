package com.game.code.Socket;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.battlefiled.RandomPlacer;
import com.game.code.components.*;
import com.game.code.utils.Bounds;
import com.game.code.utils.Mappers;

import java.util.Random;

public class OnlineGameButtonSystem extends EntitySystem implements OnlineGameButtonController {

    private final EntityBuilder entityBuilder;
    private final RandomPlacer randomPlacer;

    private final Array<OnlineGameData> creationQueue = new Array<>();
    private final ObjectMap<String, Entity> onlineGames = new ObjectMap<>();

    public OnlineGameButtonSystem(EntityBuilder entityBuilder, Bounds bounds) {
        this.entityBuilder = entityBuilder;

        randomPlacer = new RandomPlacer(bounds, new Random());
    }

    @Override
    public void updateButtonWith(OnlineGameData onlineGameData) {
        if(!onlineGames.containsKey(onlineGameData.id())) {
            creationQueue.add(onlineGameData);
            return;
        }

        refresh(onlineGames.get(onlineGameData.id()), onlineGameData);
    }

    public void refresh(Entity onlineGameButton, OnlineGameData newData) {
        Mappers.get(TextComponent.class, onlineGameButton).label.setText(prettyPrintData(newData));
    }

    private String prettyPrintData(OnlineGameData onlineGameData) {
        return "[%?BLACKEN]Name:" + onlineGameData.name() + "\n State:" + onlineGameData.state().name().toLowerCase();
    }


    @Override
    public void update(float deltaTime) {
        creationQueue.forEach(this::createButtonBy);
        creationQueue.clear();
    }

    private void createButtonBy(OnlineGameData onlineGameData) {
        entityBuilder.build("onlineGameButton");

        Body buttonBody = entityBuilder.getComponent(BodyComponent.class).body;

        if(randomPlacer.hasNext())
            buttonBody.setTransform(randomPlacer.nextSpot(), (float)(int)(Math.random() * 360));

        Entity button = entityBuilder.getEntity();

        getEngine().addEntity(button);

        entityBuilder.build("ButtonTrigger");

        entityBuilder.getComponent(ConnectedComponent.class).target = buttonBody;
        entityBuilder.getComponent(InheritAngleComponent.class).target = button;

        entityBuilder.getComponent(ContactEventComponent.class).event = onlineGameData.event();

        getEngine().addEntity(entityBuilder.getEntity());

        onlineGames.put(onlineGameData.id(), button);
        refresh(button, onlineGameData);
    }
}
