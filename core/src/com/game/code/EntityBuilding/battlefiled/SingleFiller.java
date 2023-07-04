package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.function.Predicate;

public class SingleFiller implements Filler {

    private Placer placer;
    private EntityTemplate filling;
    private final Array<Predicate<Vector2>> filters;

    public SingleFiller(Placer placer, EntityTemplate filler) {
        this.placer = placer;
        this.filling = filler;

        filters = new Array<>();
    }

    @Override
    public void fill(BattleFieldTemplate battleFieldTemplate) {
        while (placer.hasNextFor(filling)) {
            Vector2 spot = placer.nextSpotFor(filling);
            filters.forEach(filter -> filter.test(spot));

            battleFieldTemplate.put(spot, filling);
        }
    }

    @Override
    public void setPlacer(Placer placer) {
        this.placer = placer;
    }

    @Override
    public void setFilling(EntityTemplate filling) {
        this.filling = filling;
    }

    @Override
    public void addFilter(Predicate<Vector2> filter) {
        filters.add(filter);
    }


}
