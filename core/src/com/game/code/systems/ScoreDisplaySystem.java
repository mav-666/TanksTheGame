package com.game.code.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.components.TextComponent;
import com.github.tommyettinger.textra.TypingLabel;

public class ScoreDisplaySystem extends EntitySystem {

    private final Viewport viewport;
    private final EntityBuilder entityBuilder;

    private final ObjectMap<String, Integer> playerScore;

    private TypingLabel label;
    private Vector2 offset;

    public ScoreDisplaySystem(Viewport viewport, EntityBuilder entityBuilder, ObjectMap<String, Integer> playerScore) {
        this.viewport = viewport;
        this.entityBuilder = entityBuilder;
        this.playerScore = playerScore;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        createScoreText(entityBuilder);
    }

    private void createScoreText(EntityBuilder entityBuilder) {
        entityBuilder.build("GUIText");
        TextComponent textC = entityBuilder.getComponent(TextComponent.class);
        offset = textC.offset;
        label = textC.label;
        label.setText(prettyPrint());

        getEngine().addEntity(entityBuilder.getEntity());
    }

    private String prettyPrint() {
        return "SCORE:" + playerScore.get("player", 0);
    }

    @Override
    public void update(float deltaTime) {
        offset.set(0, -viewport.getWorldHeight()/2f + 0.5f);
        label.setText(prettyPrint());
        label.skipToTheEnd();
    }
}
