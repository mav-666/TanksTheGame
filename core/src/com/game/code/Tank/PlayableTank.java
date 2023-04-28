package com.game.code.Tank;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.code.AssetManagment.AssetProcessor;
import com.game.code.Entity.Entity;
import com.game.code.Tank.Cab.Cab;
import com.game.code.Tank.Cab.MoveDirection;
import com.game.code.Tank.Head.Head;
import com.game.code.Tank.Cab.TurnDirection;
import com.game.code.utils.box2d.BodyData;

public class PlayableTank extends Tank {
    private Tank tank;

    public PlayableTank(Tank tank) {
        this.tank = tank;

        addActor(tank);

        setSize(tank.getWidth(), tank.getHeight());

        ((BodyData) tank.getBody().getUserData()).owner = this;



        setName("PlayableTank");
    }

    @Override
    public void request(AssetProcessor assetRequestProcessor) {
        tank.request(assetRequestProcessor);
    }

    @Override
    public void setCab(Cab cab) {
        tank.setCab(cab);
    }

    @Override
    public void setHead(Head head) {
        tank.setHead(head);
    }


    @Override
    public void act(float delta) {
        super.act(delta);

        if(tank.isTouchable()) processInput(delta);
    }

    protected void processInput(float delta) {
        boolean canTurn= false;

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            move(delta, MoveDirection.forward);
            canTurn = true;
        }

        else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            move(delta, MoveDirection.backward);
            canTurn = true;
        }

        if(canTurn) {
            if (Gdx.input.isKeyPressed(Input.Keys.A))
                turn(delta, TurnDirection.left);

            if (Gdx.input.isKeyPressed(Input.Keys.D))
                turn(delta, TurnDirection.right);
        }


        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            rotateHead(delta, TurnDirection.left);

        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            rotateHead(delta, TurnDirection.right);


        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            shoot();
    }

    @Override
    public void move(float delta, MoveDirection direction) {
        tank.move(delta, direction);
    }

    @Override
    public void turn(float delta, TurnDirection direction) {
        tank.turn(delta, direction);
    }

    @Override
    public void rotateHead(float delta, TurnDirection direction) {
        tank.rotateHead(delta, direction);
    }

    @Override
    public void shoot() {
        tank.shoot();
    }


    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public Tank getTank() {
        return tank;
    }


    @Override
    public TankData getTankData() {
        return tank.getTankData();
    }

    @Override
    public Body getBody() {
        return tank.getBody();
    }

    @Override
    public void collusionRespond(Entity participant) {
        tank.collusionRespond(participant);
    }

    @Override
    public float getHealth() {
        return tank.getHealth();
    }

    @Override
    public boolean takeDamage(float damage) {
        return tank.takeDamage(damage);
    }

    @Override
    public void die() {
        tank.die();
    }
}
