package com.game.code.BattleField;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


import java.util.HashSet;
import java.util.Random;

public interface BattleFieldBuilder {

    default void buildBattleField() {
        resetFreeSpace();
    }

    BattleField createBattleField();

    HashSet<Vector2> getFreeSpace();

    Random getRandom();

    default void resetFreeSpace() {
        getFreeSpace().clear();
        for(int i = 0; i < getBattleFieldWidth(); i++) {
            for(int j = 0; j < getBattleFieldHeight(); j++) {
                getFreeSpace().add(new Vector2(i, j));
            }
        }
    }


    float getBattleFieldWidth();

    float getBattleFieldHeight();

    float getBattleFieldTileWidth();

    float getBattleFieldTileHeight();

    World getBattleFieldWorld();


}
