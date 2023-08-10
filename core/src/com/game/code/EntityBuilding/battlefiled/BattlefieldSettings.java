package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.utils.ObjectFloatMap;

public class BattlefieldSettings {
    private final ObjectFloatMap<String> fillingPercentages = new ObjectFloatMap<>();

    public void setPercentageOf(String fillingName, float percentage) {
        fillingPercentages.put(fillingName, percentage);
    }

    public float getPercentageOf(String fillingName) {
        return fillingPercentages.get(fillingName, 0);
    }

    public void levelAllValues() {
        float sum = calculateSumPercent();
        if(sum <= 100) return;

        float balanceModifier = calculateBalanceModifier(sum);
        fillingPercentages.keys().forEach((fillingName) -> transformParameter(fillingName, balanceModifier));
    }

    private float calculateSumPercent() {
        float sum = 0;
        for(float percentage :  fillingPercentages.values().toArray().toArray())
            sum += percentage;

        return sum;
    }

    private float calculateBalanceModifier(float sumPercent) {
        return sumPercent/100;
    }

    private void transformParameter(String fillingName, float modifier) {
        fillingPercentages.put(fillingName, fillingPercentages.get(fillingName, 0) / modifier);
    }
}
