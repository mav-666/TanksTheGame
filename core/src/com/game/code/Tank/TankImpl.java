package com.game.code.Tank;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import com.game.code.AssetManagment.AssetProcessor;
import com.game.code.utils.box2d.BodyData;

import com.game.code.Entity.Entity;
import com.game.code.Tank.Cab.Cab;
import com.game.code.Tank.Cab.MoveDirection;
import com.game.code.Tank.Cab.TurnDirection;
import com.game.code.Tank.Head.Head;

public class TankImpl extends Tank {
    protected Cab cab;
    protected Head head;

    private final TankData tankData;

    protected TankImpl(Vector2 pos, float width, float height) {
        tankData = new TankData();

        setPosition(pos.x, pos.y);
        setSize(width, height);
        setOrigin(width/2, height/2);
    }
    @Override
    public void request(AssetProcessor assetRequestProcessor) {
        cab.request(assetRequestProcessor);
        head.request(assetRequestProcessor);
    }

    @Override
    public void setCab(Cab cab) {
        this.cab = cab;
        ((BodyData) cab.getBody().getUserData()).owner = this;
        if(head != null) {
            addActorBefore(head, cab);
            return;
        }
        addActor(cab);
        tankData.cabData = cab.getCabData();
    }

    @Override
    public void setHead(Head head) {
        this.head= head;

        if(cab != null) {
            addActorAfter(cab, head);
            return;
        }
        addActor(head);
        tankData.headData = head.getHeadData();
    }

    @Override
    public void move(float delta, MoveDirection direction) {
        cab.move(delta, direction);
    }

    @Override
    public void turn(float delta, TurnDirection direction) {
        cab.turn(delta, direction);
    }

    @Override
    public void rotateHead(float delta, TurnDirection direction) {
        head.rotateHead(delta, direction);
    }

    @Override
    public void shoot() {
        head.shoot();
    }

    @Override
    public TankData getTankData() {
        return tankData;
    }

    @Override
    public Body getBody() {
        return cab.getBody();
    }

    @Override
    public void collusionRespond(Entity participant) {
        cab.collusionRespond(participant);
    }

    @Override
    public float getHealth() {
        return cab.getHealth();
    }

    @Override
    public boolean takeDamage(float damage) {
        return cab.takeDamage(damage);
    }

    @Override
    public void die() {
        cab.die();
    }

}