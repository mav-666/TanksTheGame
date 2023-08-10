package com.game.code.utils;

public record Bounds(
        float startX,
        float startY,

        float width,
        float height
) {
    public Bounds(float width, float height) {
        this(0,0, width, height);
    }
}
