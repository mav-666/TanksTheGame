package com.game.code.systems.Render;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Align;
import com.game.code.components.*;
import com.game.code.utils.Mappers;

import java.util.Comparator;

public class RenderingSystem extends SortedIteratingSystem {

    public final static float PPM = 16f;

    private final Mappers mappers = Mappers.getInstance();

    private final Batch batch;
    private final Camera camera;

    private final ComponentMapper<TextureComponent> textureM = mappers.getMapper(TextureComponent.class);
    private final ComponentMapper<ParticleComponent> particleM = mappers.getMapper(ParticleComponent.class);
    private final ComponentMapper<TextComponent> textM = mappers.getMapper(TextComponent.class);
    private final ComponentMapper<WidgetComponent> widgetM = mappers.getMapper(WidgetComponent.class);
    private final ComponentMapper<TransformComponent> transformM = mappers.getMapper(TransformComponent.class);

    private Vector2 position;


    public RenderingSystem(Camera camera, Batch batch) {
        super(Family.all(TransformComponent.class).one(TextureComponent.class, ParticleComponent.class, TextComponent.class, WidgetComponent.class).get(), new ZComparator(), 40);

        this.camera = camera;
        this.batch = batch;
    }

    public static float toMeters(float pixels) {
        return pixels/PPM;
    }

    public static float toPixels(float meters) {
        return meters*PPM;
    }

    @Override
    public void update(float deltaTime) {
        batch.setProjectionMatrix(camera.combined);
        batch.enableBlending();
        batch.begin();
        super.update(deltaTime);
        batch.end();

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        position = transformM.get(entity).position;

        if(mappers.has(InvisibleComponent.class, entity))
            return;

        if(textureM.has(entity))
            drawTexture(entity);

        if(particleM.has(entity))
            drawParticle(entity, deltaTime);

        if(textM.has(entity))
            drawText(entity, deltaTime);

        if(widgetM.has(entity))
            drawWidget(entity, deltaTime);

        batch.setColor(Color.WHITE);
    }

    private void drawTexture(Entity entity) {
        TransformComponent transform = transformM.get(entity);
        TextureComponent texture = textureM.get(entity);

        float width = texture.width;
        float height = texture.height;

        texture.offset.rotateDeg(transform.degAngle);

        float posX = transform.position.x - width/2 + texture.offset.x;
        float posY = transform.position.y - height/2 + texture.offset.y;

        float originX = transform.origin.x + width/2;
        float originY = transform.origin.y + height/2;

        if(mappers.has(ColorComponent.class, entity))
            batch.setColor(mappers.get(ColorComponent.class, entity).color);

        batch.draw(texture.textureRegion,
                posX, posY,
                originX, originY,
                width, height,
                texture.scaleX, texture.scaleY,
                transform.degAngle);

        texture.offset.rotateDeg(-transform.degAngle);
    }

    private void drawParticle(Entity entity, float deltaTime) {
        ParticleComponent particle = particleM.get(entity);

        if(particle.particleEffect.isComplete())
            entity.add(getEngine().createComponent(DestroyedComponent.class));

        particle.particleEffect.scaleEffect(1/PPM);
        particle.particleEffect.setPosition(position.x, position.y);
        particle.particleEffect.update(deltaTime);
        particle.particleEffect.draw(batch, deltaTime);
    }

    private void drawText(Entity entity, float deltaTime) {
        Vector2 offset = textM.get(entity).offset;
        Widget label = textM.get(entity).label;

        if(mappers.has(ColorComponent.class, entity))
            label.setColor(mappers.get(ColorComponent.class, entity).color);

        drawActor(label, offset, deltaTime);
        batch.setColor(Color.WHITE);
    }

    private void drawWidget(Entity entity, float deltaTime) {
        Vector2 offset = widgetM.get(entity).offset;
        Actor widget = widgetM.get(entity).widget;

        drawActor(widget, offset, deltaTime);
        batch.setColor(Color.WHITE);
    }

    private void drawActor(Actor widget, Vector2 offset, float deltaTime) {
        float posX = position.x + offset.x;
        float posY = position.y + offset.y;

        widget.setPosition(posX, posY, Align.center);

        widget.act(deltaTime);
        widget.draw(batch, 1);
        batch.setColor(Color.WHITE);
    }

    public static class ZComparator implements Comparator<Entity> {

        private final ComponentMapper<TransformComponent> transformM = Mappers.getInstance().getMapper(TransformComponent.class);

        @Override
        public int compare(Entity o1, Entity o2) {
            float pos1 = transformM.get(o1).zIndex;
            float pos2 = transformM.get(o2).zIndex;

            return (int) (Math.signum(pos2 - pos1));
        }
    }
}
