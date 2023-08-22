package com.game.code.systems.HUD;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.components.TextComponent;
import com.game.code.utils.MatchTime;
import com.github.tommyettinger.textra.TypingLabel;

public class TimeSystem extends IntervalSystem {

    private final Viewport viewport;
    private final EntityBuilder entityBuilder;

    private final MatchTime matchTime;

    private TypingLabel label;
    private Vector2 offset;


    public TimeSystem(Viewport viewport, EntityBuilder entityBuilder, MatchTime matchTime) {
        super(1);

        this.viewport = viewport;
        this.entityBuilder = entityBuilder;
        this.matchTime = matchTime;
    }

    @Override
    public void addedToEngine(Engine engine) {
        createTimerText(entityBuilder);
    }

    private void createTimerText(EntityBuilder entityBuilder) {
        entityBuilder.build("GUIText");
        TextComponent textC = entityBuilder.getComponent(TextComponent.class);
        offset = textC.offset;
        label = textC.label;
        label.setText(matchTime.toString());

        getEngine().addEntity(entityBuilder.getEntity());
    }

    @Override
    public void update(float deltaTime) {
        offset.set(0, viewport.getWorldHeight()/2f - 0.125f);
        super.update(deltaTime);
    }

    @Override
    protected void updateInterval() {
       matchTime.updateTime();

       label.setText(matchTime.toString());
    }
}
