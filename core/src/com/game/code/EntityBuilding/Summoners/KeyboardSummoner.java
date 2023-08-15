package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.components.*;
import com.github.tommyettinger.textra.TypingLabel;
import org.apache.commons.lang3.StringUtils;

public class KeyboardSummoner extends EntityBuilderSummoner {
    private static final String KEY_SYMBOLS = "abcdefghijklmnopqrstuvwxyz-_1234567890";

    private final Skin skin;

    private Vector2 pos;
    private TypingLabel nameLabel;
    private Color textColor;

    public KeyboardSummoner(EntityBuilder entityBuilder, Engine engine, Skin skin) {
        super(entityBuilder, engine);

        this.skin = skin;
    }

    @Override
    public Entity summonBy(Entity summoner) {
        pos = mappers.get(TransformComponent.class, summoner).position;

        createTitleText();

        createTextField();

        Entity scroll = createScroll();

        createLeftKey(scroll);

        createRightKey(scroll);

        createEraseKey();

        createApplyKey();

        return scroll;
    }
    private void createTitleText() {
        entityBuilder.build("Text");
        entityBuilder.getComponent(TransformComponent.class).position.set(pos.x, pos.y + 2f);

        entityBuilder.getComponent(TextComponent.class).label.setText("Your name is:");
        entityBuilder.getComponent(ColorComponent.class).color.set(skin.getColor("brown"));

        engine.addEntity(entityBuilder.getEntity());
    }

    private void createTextField() {
        entityBuilder.build("Text");
        entityBuilder.getComponent(TransformComponent.class).position.set(pos.x, pos.y + 1.5f);

        nameLabel = entityBuilder.getComponent(TextComponent.class).label;
        textColor = entityBuilder.getComponent(ColorComponent.class).color;

        engine.addEntity(entityBuilder.getEntity());
    }

    private Entity createScroll() {
        entityBuilder.build("Scroll");
        entityBuilder.getComponent(TransformComponent.class).position.set(pos.x, pos.y + 1f);

        ScrollComponent scroll = entityBuilder.getComponent(ScrollComponent.class);
        entityBuilder.getComponent(ScrollsComponent.class).scrollingDirection = 1;
        scroll.currentItemGroup = -1;

        Entity scrollE = entityBuilder.getEntity();
        engine.addEntity(scrollE);

        for(char symbol : KEY_SYMBOLS.toCharArray()) {
            scroll.allItems.add(createKey(symbol));
        }

        return scrollE;
    }

    private Entity createKey(char symbol) {
        entityBuilder.build("TextButton");

        entityBuilder.getComponent(TextComponent.class).label.setText(String.valueOf(symbol));
        entityBuilder.getComponent(ContactEventComponent.class).event = () -> {
            nameLabel.setText(nameLabel.getOriginalText().append(symbol).toString());
            textColor.set(skin.getColor("brown"));
        };

        engine.addEntity(entityBuilder.getEntity());

        return entityBuilder.getEntity();
    }

    private void createLeftKey(Entity scroll) {
        entityBuilder.build("TextButton");
        entityBuilder.getComponent(BodyComponent.class).body.setTransform(pos.x - 1.25f, pos.y + 0.5f, 0);

        entityBuilder.getComponent(TextComponent.class).label.setText("<<<");
        entityBuilder.getComponent(ContactEventComponent.class).event = () -> {
            ScrollsComponent scrolls = engine.createComponent(ScrollsComponent.class);
            scrolls.scrollingDirection = -1;
            scroll.add(scrolls);
        };

        engine.addEntity(entityBuilder.getEntity());
    }

    private void createRightKey(Entity scroll) {
        entityBuilder.build("TextButton");
        entityBuilder.getComponent(BodyComponent.class).body.setTransform(pos.x + 1.25f, pos.y + 0.5f, 0);

        entityBuilder.getComponent(TextComponent.class).label.setText(">>>");
        entityBuilder.getComponent(ContactEventComponent.class).event = () -> {
            ScrollsComponent scrolls = engine.createComponent(ScrollsComponent.class);
            scrolls.scrollingDirection = 1;
            scroll.add(scrolls);
        };

        engine.addEntity(entityBuilder.getEntity());
    }

    private void createEraseKey() {
        entityBuilder.build("TextButton");
        entityBuilder.getComponent(BodyComponent.class).body.setTransform(pos.x + 1.5f, pos.y, 0);

        entityBuilder.getComponent(TextComponent.class).label.setText("<--");
        entityBuilder.getComponent(ContactEventComponent.class).event = () -> {
            nameLabel.setText(StringUtils.chop(nameLabel.getOriginalText().toString()));
            textColor.set(skin.getColor("brown"));
        };

        engine.addEntity(entityBuilder.getEntity());
    }

    private void createApplyKey() {
        entityBuilder.build("TextButton");
        entityBuilder.getComponent(BodyComponent.class).body.setTransform(pos.x - 1.5f, pos.y, 0);

        entityBuilder.getComponent(TextComponent.class).label.setText("√");
        entityBuilder.getComponent(ContactEventComponent.class).event = () -> {
            saveName();
            textColor.set(skin.getColor("lightBrown"));
        };

        engine.addEntity(entityBuilder.getEntity());
    }

    private void saveName() {
        Preferences prefs = Gdx.app.getPreferences("Prefs");

        prefs.putString("playerName", nameLabel.getOriginalText().toString());
        prefs.flush();

    }

    @Override
    public SummonerType getType() {
        return SummonerType.Keyboard;
    }
}
