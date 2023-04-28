package com.game.code.UI;

public class PPM {
    public static final float RATIO = 1/52f;

    public static float toMeters(float pixels) {
        return pixels * RATIO;
    }

    public static float toPixels(float meters) {
        return meters / RATIO;
    }
}
