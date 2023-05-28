package com.game.code.BattleField;

public class PercentParameter implements ContentParameter<Float> {
    private float percentage;

    @Override
    public void setValue(Float value) {
        this.percentage = value;
    }

    @Override
    public Float getValue() {
        return percentage;
    }

    public Float getMax() {
        return 1f;
    }

    public Float getMin() {
        return 0f;
    }
}
