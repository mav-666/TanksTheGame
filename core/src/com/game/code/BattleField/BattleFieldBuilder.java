package com.game.code.BattleField;

import com.badlogic.gdx.math.Vector2;


import java.util.HashMap;

public interface BattleFieldBuilder {

     default void resetMap() {
        for(int i = 0; i < getBattleField().getWidth(); i++) {
            for(int j = 0; j < getBattleField().getHeight(); j++) {
                getFreeSpace().put(
                        new Vector2(getBattleField().getWidth() - i, getBattleField().getHeight() - j),
                        new Vector2(i, j)
                );
            }
        }
        getBattleField().clear();
    }

    void buildBattleField();

    BattleField getBattleField();

    HashMap<Vector2, Vector2> getFreeSpace();

}
