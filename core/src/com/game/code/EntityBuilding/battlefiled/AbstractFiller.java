package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.function.Predicate;

public abstract class AbstractFiller {

    protected final Array<Predicate<Vector2>> filters = new Array<>();

    protected BattleFieldTemplate battleFieldTemplate;
    protected int currentPlaced = 0;
    protected int maxPlaced = -1;

    public void start(BattleFieldTemplate battleFieldTemplate) {
        this.battleFieldTemplate = battleFieldTemplate;
    }

    public void addFilter(Predicate<Vector2> filter) {
        filters.add(filter);
    }

    public void setMaxPlaced(int maxPlaced) {
        this.maxPlaced = maxPlaced;
    }

    protected boolean isntEnough() {
        return maxPlaced == -1 || currentPlaced < maxPlaced;
    }

    public abstract void fillBy(Placer placer, float zIndex, EntityTemplate filling);
}
