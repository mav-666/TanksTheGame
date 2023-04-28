package com.game.code.Tank.Head;

import com.game.code.AssetManagment.AssetRequest;
import com.game.code.utils.scene2d.ColoredGroup;
import com.game.code.Entity.Entity;
import com.game.code.Tank.Cab.TurnDirection;
import com.game.code.utils.scene2d.TextureActor;

public abstract class Head extends ColoredGroup implements AssetRequest {
    protected final Entity owner;

    protected TextureActor head;
    protected TextureActor barrel;

    protected final HeadData headData;

    protected Head(float width, float height, Entity owner, HeadData headData) {
        this.owner = owner;
        this.headData = headData;

        setOrigin(width/2, height/2);

        head = new TextureActor();
        head.setSize(width/2, height/2);
        head.setPosition(width/2 - head.getWidth()/2, height/2 - head.getHeight()/2);

        barrel = new TextureActor();
        barrel.setSize(head.getWidth()/3, head.getHeight()*1.25f);
        barrel.setPosition(width/2 - barrel.getWidth()/2, height/2);
        barrel.setOrigin(barrel.getX() + barrel.getWidth()/2, 0);

        addActor(barrel);
        addActor(head);

    }

    public void rotateHead(float delta, TurnDirection direction) {
        rotateBy(headData.rotationSpeed * delta * direction.mult);
    }

    public abstract void shoot();

    public HeadData getHeadData() {
        return headData;
    }


}
