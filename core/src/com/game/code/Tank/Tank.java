package com.game.code.Tank;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import com.game.code.AssetManagment.AssetRequest;
import com.game.code.AssetManagment.AssetRequestProcessor;
import com.game.code.BodyData;
import com.game.code.ColoredGroup;
import com.game.code.Entity.Breakable;
import com.game.code.Entity.Entity;
import com.game.code.Tank.Head.Head;

public class Tank extends ColoredGroup implements Breakable, AssetRequest {
    private String id;

    protected Cab cab;
    protected Head head;

    protected Tank(Vector2 pos, float width, float height) {
        setPosition(pos.x, pos.y);
        setSize(width, height);
        setOrigin(width/2, height/2);
    }

    @Override
    public void request(AssetRequestProcessor assetRequestProcessor) {
        cab.request(assetRequestProcessor);
        head.request(assetRequestProcessor);
    }

    @Override
    public void passAssets(AssetRequestProcessor assets) {}

    public void setCab(Cab cab) {
        this.cab = cab;
        ((BodyData) cab.getBody().getUserData()).owner = this;
        if(head != null) {
            addActorBefore(head, cab);
            return;
        }
        addActor(cab);
    }

    public void setHead(Head head) {
        this.head= head;

        if(cab != null) {
            addActorAfter(cab, head);
            return;
        }
        addActor(head);
    }

    public void move(float delta, MoveDirection direction) {
        cab.move(delta, direction);
    }

    public void turn(float delta, TurnDirection direction) {
        cab.turn(delta, direction);
    }

    public void rotateHead(float delta, TurnDirection direction) {
        head.rotateHead(delta, direction);
    }

    public void shoot() {
        head.shoot();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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