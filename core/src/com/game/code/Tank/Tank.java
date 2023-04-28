package com.game.code.Tank;

import com.game.code.AssetManagment.AssetProcessor;
import com.game.code.AssetManagment.AssetRequest;
import com.game.code.Entity.Breakable;
import com.game.code.Tank.Cab.Cab;
import com.game.code.Tank.Cab.MoveDirection;
import com.game.code.Tank.Cab.TurnDirection;
import com.game.code.Tank.Head.Head;
import com.game.code.utils.scene2d.ColoredGroup;

public abstract class Tank extends ColoredGroup implements Breakable, AssetRequest {
    @Override
    public void passAssets(AssetProcessor assets) {

    }

    abstract public void move(float delta, MoveDirection direction);

    abstract public void turn(float delta, TurnDirection direction);

    abstract public void rotateHead(float delta, TurnDirection direction);

    abstract public void shoot();

    abstract public void setCab(Cab cab);

    abstract public void setHead(Head head);

    abstract public TankData getTankData();

}
