package com.game.code.EntityBuilding.Summoners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.SummoningDirector;
import com.game.code.components.*;
import com.game.code.utils.Mappers;

public class EntitySummoner extends EntityBuilderSummoner implements SummoningDirector {

    private final Vector2 vertex = new Vector2();

    private String entityName;

    public EntitySummoner(EntityBuilder entityBuilder, Engine engine) {
        super(entityBuilder, engine);
    }

    @Override
    public Entity summonBy(Entity summoner) {
        entityName = Mappers.get(SummonsNowComponent.class, summoner).entityName;

        build();

        init(summoner);

        engine.addEntity(entityBuilder.getEntity());

        return entityBuilder.getEntity();
    }

    protected void build() {
        entityBuilder.build(entityName);
    }

    protected void init(Entity summoner) {
        initTransformBy(summoner);
    }

    protected void initTransformBy(Entity summoner) {
        SummonsComponent summonsC = Mappers.get(SummonsNowComponent.class, summoner);

        TransformComponent entityTransform = entityBuilder.getComponent(TransformComponent.class);
        TransformComponent summonerTransform = Mappers.get(TransformComponent.class, summoner);

        entityTransform.position.set(summonerTransform.position).add(summonsC.offset);
        entityTransform.degAngle = summonsC.degAngle;
        entityTransform.zIndex += summonerTransform.zIndex;

        initScale(summonsC);

        if(entityBuilder.hasComponent(BodyComponent.class))
            initBody(summonerTransform.position, summonsC);
    }

    private void initBody(Vector2 pos, SummonsComponent summonsC) {
        Body body = entityBuilder.getComponent(BodyComponent.class).body;
        body.getFixtureList().forEach(fixture -> {
            boolean isSensor = fixture.isSensor();
            initShapeScale(fixture.getShape(), summonsC.scaleX, summonsC.scaleY);
            fixture.setSensor(isSensor);
        });
        body.setTransform(pos, summonsC.degAngle);
    }

    //Workaround :(
    private void initShapeScale(Shape shape, float scaleX, float scaleY) {
        if(shape.getType() == Shape.Type.Polygon) {
            PolygonShape polygon = ((PolygonShape) shape);
            polygon.getVertex(2, vertex);
            polygon.setAsBox(vertex.x * scaleX, vertex.y * scaleY);
        } else
            shape.setRadius(shape.getRadius() * scaleX);
    }

    protected void initScale(SummonsComponent summonsComponent) {
        if(!entityBuilder.hasComponent(TextureComponent.class))
            return;
        
        TextureComponent texture = entityBuilder.getComponent(TextureComponent.class);
        texture.scaleX = summonsComponent.scaleX;
        texture.scaleY = summonsComponent.scaleY;
    }

    @Override
    public SummonerType getType() {
        return SummonerType.Default;
    }
}
