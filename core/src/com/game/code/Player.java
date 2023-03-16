package com.game.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.game.code.Tank.MoveDirection;
import com.game.code.Tank.Tank;
import com.game.code.Tank.TurnDirection;

public class Player extends Actor {
    private Tank tank;

    public Player(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(tank.isTouchable()) processInput(delta);
    }

    private void processInput(float delta) {
        boolean canTurn= false;

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            tank.move(delta, MoveDirection.forward);
            canTurn = true;
        }

        else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            tank.move(delta, MoveDirection.backward);
            canTurn = true;
        }

        if(canTurn) {
            if (Gdx.input.isKeyPressed(Input.Keys.A))
                tank.turn(delta, TurnDirection.left);

            if (Gdx.input.isKeyPressed(Input.Keys.D))
                tank.turn(delta, TurnDirection.right);
        }


        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            tank.rotateHead(delta, TurnDirection.left);

        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            tank.rotateHead(delta, TurnDirection.right);


        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            tank.shoot();

    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }


}
