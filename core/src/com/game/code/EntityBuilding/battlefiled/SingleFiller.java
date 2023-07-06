package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;

public class SingleFiller extends AbstractFiller {

    @Override
    public void fillBy(Placer placer, float zIndex, EntityTemplate filling) {
        reset(placer);

        do {
            Vector2 spot = placer.nextSpot();
            filters.forEach(filter -> filter.test(spot));

            battleFieldTemplate.put(spot, zIndex, filling);
            currentPlaced++;
        } while (placer.hasNext() && isntEnough());
    }

    private void reset(Placer placer) {
        currentPlaced = 0;
        placer.reset(placer.getBounds());

    }
}
