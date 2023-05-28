package com.game.code.BattleField;

import java.util.ArrayList;

public class PercentBalancer {
    private final float MAX_SUM;
    private final ArrayList<PercentParameter> percents;

    public PercentBalancer(ArrayList<PercentParameter> percents, float maxSum) {
        this.MAX_SUM = maxSum;
        this.percents = percents;
    }

    public void balancePercents() {
        float balanceModifier = calculateBalanceModifier();
        percents.forEach((parameter) -> transformParameter(parameter, balanceModifier));
    }

    private float calculateBalanceModifier() {
        float sum = 0;
        for(PercentParameter percent : percents) {
            sum += percent.getValue();
        }

        return sum/MAX_SUM;
    }

    private void transformParameter(PercentParameter parameter, float modifier) {
        parameter.setValue(parameter.getValue()/modifier);
    }
}
