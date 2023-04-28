package com.game.code.utils.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class ColoredGroup extends Group {

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        getChildren().forEach(actor -> actor.setColor(color));
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        super.setColor(r, g, b, a);
        getChildren().forEach(actor -> actor.setColor(r, g, b, a));
    }

    @Override
    protected void drawChildren(Batch batch, float parentAlpha) {
        getChildren().forEach(actor -> actor.setColor(getColor()));
        super.drawChildren(batch, parentAlpha);
    }
}
