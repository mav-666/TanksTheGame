package com.game.code.PowerUp;

import com.badlogic.gdx.graphics.Color;

public enum ValuePowerUps {
    projectileSpeed(
        (tank, val) -> {
            tank.getTankData().headData.projectileSpeed *= val;
        },
        (tank, val) -> {
            tank.getTankData().headData.projectileSpeed /= val;
        },
        Color.valueOf("13b2f2")),
    speed(
        (tank, val) -> {
            tank.getTankData().cabData.speed *= val;
        },
        (tank, val) -> {
            tank.getTankData().cabData.speed /= val;
        },
        Color.valueOf("f7ac37")),
    strength(
        (tank, val) -> {
            tank.getTankData().headData.damage *= val;
        },
        (tank, val) -> {
            tank.getTankData().headData.damage /= val;
        },
        Color.valueOf("c42c36")),
    recharge(
        (tank, val) -> {
        tank.getTankData().headData.recharge /= val;
        },
        (tank, val) -> {
        tank.getTankData().headData.recharge *= val;
        },
        Color.valueOf("509b4b")),
    ;

    ValuePower power;
    UndoValuePower undo;
    Color color;

    ValuePowerUps(ValuePower power, UndoValuePower undo, Color color) {
        this.power = power;
        this.undo = undo;
        this.color = color;
    }
}
