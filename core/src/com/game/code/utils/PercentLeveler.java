package com.game.code.utils;

import com.badlogic.gdx.utils.ObjectFloatMap;

public class PercentLeveler {

    private ObjectFloatMap<String> percents;

    public void levelAllValues(ObjectFloatMap<String> percents) {
        this.percents = percents;
        float sum = calculateSumPercent();
        if(sum <= 100) return;

        float balanceModifier = calculateBalanceModifier(sum);
        new ObjectFloatMap.Keys<>(percents).forEach((key) -> transformParameter(key, balanceModifier));
    }

    private float calculateSumPercent() {
        float sum = 0;
        ObjectFloatMap.Values percentages = new ObjectFloatMap.Values(percents);
        while (percentages.hasNext())
            sum += percentages.next();

        return sum;
    }

    private float calculateBalanceModifier(float sumPercent) {
        return sumPercent/100;
    }

    private void transformParameter(String fillingName, float modifier) {
        percents.put(fillingName, percents.get(fillingName, 0) / modifier);
    }
}
