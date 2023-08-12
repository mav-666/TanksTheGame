package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.utils.ObjectFloatMap;

public record GameSettings(
        ObjectFloatMap<String> fillingPercentages,
        ObjectFloatMap<String> otherSettings
) {
    public GameSettings() {
        this(new ObjectFloatMap<>(), new ObjectFloatMap<>());
    }
}
