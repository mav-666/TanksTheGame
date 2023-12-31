package com.game.code.systems.Box2d.Contact;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;
import com.game.code.components.*;
import com.game.code.utils.Mappers;

public class CollusionSystem extends IteratingSystem implements EntityListener {
    public static final Family FAMILY = Family.all(CollusionComponent.class).get();
    private final ComponentMapper<StartCollusionComponent> startCollusionM = Mappers.getMapper(StartCollusionComponent.class);
    private final ComponentMapper<CollusionComponent> collusionM = Mappers.getMapper(CollusionComponent.class);
    private final ComponentMapper<EndCollusionComponent> endCollusionM = Mappers.getMapper(EndCollusionComponent.class);

    public CollusionSystem() {
        super(Family.one(StartsCollusionComponent.class, EndsCollusionComponent.class).get(), 3);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.addEntityListener(FAMILY, this);
    }

    @Override
    public void entityAdded(Entity entity) {
        entity.add(getEngine().createComponent(StartCollusionComponent.class));
        entity.add(getEngine().createComponent(EndCollusionComponent.class));
    }

    @Override
    public void entityRemoved(Entity entity) {

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if(Mappers.has(StartsCollusionComponent.class, entity))
            removeStartsCollusion(entity);

        if(Mappers.has(EndsCollusionComponent.class, entity))
            removeEndsCollusion(entity);

    }

    private void removeStartsCollusion(Entity entity) {
        Array<Entity> involved = startCollusionM.get(entity).involved;
        involved.forEach((involvedE) -> addCollusionComponent(entity, involvedE));
        involved.clear();

        entity.remove(StartsCollusionComponent.class);
    }

    private void addCollusionComponent(Entity A, Entity B) {
        if(collusionM.has(A)) {
            collusionM.get(A).involved.add(B);
        }

        A.add(getEngine().createComponent(CollidesComponent.class));
    }

    private void removeEndsCollusion(Entity entity) {
        endCollusionM.get(entity).involved.clear();
        entity.remove(EndsCollusionComponent.class);

    }
}
