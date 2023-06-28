package com.game.code.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.code.components.DestroyedComponent;
import com.game.code.components.ParticleComponent;
import com.game.code.components.TextureComponent;
import com.game.code.components.TransformComponent;
import com.game.code.utils.Mappers;

public class RenderingSystem extends SortedIteratingSystem {

    private final static float PPM = 16f;

    private final Mappers mappers;

    private final SpriteBatch batch;
    private final Camera camera;

    public RenderingSystem(Camera camera) {
        super(Family.all(TransformComponent.class).one(TextureComponent.class, ParticleComponent.class).get(), new ZComparator(), 40);

        this.camera = camera;

        mappers = Mappers.getInstance();

        batch = new SpriteBatch();

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
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if(mappers.get(TextureComponent.class).has(entity))
            processTexture(entity);

        if(mappers.get(ParticleComponent.class).has(entity))
            processParticle(entity, deltaTime);
    }

    private void processTexture(Entity entity) {
        TransformComponent transform = mappers.get(TransformComponent.class).get(entity);
        TextureComponent texture = mappers.get(TextureComponent.class).get(entity);

        float width = texture.width;
        float height = texture.height;

        texture.offset.rotateDeg(transform.degAngle);

        float posX = transform.position.x - width/2 + texture.offset.x;
        float posY = transform.position.y - height/2 + texture.offset.y;

        float originX = transform.origin.x + width/2;
        float originY = transform.origin.y + height/2;

        batch.setColor(texture.color);

        batch.draw(texture.textureRegion,
                posX, posY,
                originX, originY,
                width, height,
                texture.scaleX, texture.scaleY,
                transform.degAngle);

        batch.setColor(Color.WHITE);
        texture.offset.rotateDeg(-transform.degAngle);
    }

    private void processParticle(Entity entity, float deltaTime) {
        Vector2 position = mappers.get(TransformComponent.class).get(entity).position;
        ParticleComponent particle = mappers.get(ParticleComponent.class).get(entity);

        if(particle.particleEffect.isComplete())
            entity.add(getEngine().createComponent(DestroyedComponent.class));

        particle.particleEffect.scaleEffect(1/PPM);
        particle.particleEffect.setPosition(position.x, position.y);
        particle.particleEffect.update(deltaTime);
        particle.particleEffect.draw(batch, deltaTime);
    }
}
