package com.game.code.EntityBuilding.battlefiled;

public record Bounds(
        float startX,
        float startY,

        float width,
        float height
) {
    public Bounds(float startX, float startY, float width, float height) {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
    }

    public Bounds(float width, float height) {
        this(0,0, width, height);
    }
}
