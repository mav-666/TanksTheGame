package com.game.code.Tank.Head;

import com.game.code.AssetManagment.AssetRequest;
import com.game.code.ColoredGroup;
import com.game.code.Tank.TurnDirection;
import com.game.code.TextureEntity;

public abstract class Head extends ColoredGroup implements AssetRequest {
    protected TextureEntity head;
    protected TextureEntity barrel;

    protected float rotationSpeed;

    protected Head( float width, float height, float rotationSpeed) {
        this.rotationSpeed= rotationSpeed;

        setOrigin(width/2, height/2);

        head = new TextureEntity();
        head.setSize(width/2, height/2);
        head.setPosition(width/2 - head.getWidth()/2, height/2 - head.getHeight()/2);

        barrel = new TextureEntity();
        barrel.setSize(head.getWidth()/3, head.getHeight()*1.25f);
        barrel.setPosition(width/2 - barrel.getWidth()/2, height/2);
        barrel.setOrigin(barrel.getX() + barrel.getWidth()/2, 0);

        addActor(barrel);
        addActor(head);

    }

    public void rotateHead(float delta, TurnDirection direction) {
        rotateBy(rotationSpeed * delta * direction.mult);
    }

    public abstract void shoot();

}
