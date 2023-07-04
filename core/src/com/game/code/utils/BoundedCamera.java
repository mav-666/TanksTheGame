package com.game.code.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.game.code.EntityBuilding.battlefiled.Bounds;

//камера привязанная к объекту, не выходит за границы карты
public class BoundedCamera extends OrthographicCamera {
    private Vector2 target;
    
    private Bounds bounds;

    private float boundsX, boundsY;

    public BoundedCamera(Bounds bounds) {
        this.bounds = bounds;
    }
    
    public void follow(Vector2 target) {
        this.target = target;
    }

    public void setBorders(Bounds bounds){
        bounds = bounds;
    }

    @Override
    public void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.U)) zoom += 0.1;
        if(Gdx.input.isKeyPressed(Input.Keys.O)) zoom -= 0.1;

        boundsX = viewportWidth/2 + bounds.startX();
        boundsY = viewportHeight/2 + bounds.startX();

        if(target != null)
            lerpToTarget();

       checkBorderCrossed();

        super.update();


    }
    //красивое перемещение камеры
    private void lerpToTarget(){
        this.position.x= position.x + (target.x - position.x) * .05f;
        this.position.y= position.y + (target.y - position.y) * .05f;
    }

    //проверить пересечение границ
    private void checkBorderCrossed(){
        if(position.x < boundsX){
            position.x= boundsX;
        }
        if(position.y < boundsY){
            position.y= boundsY;

        }
        if(position.x > bounds.width() - boundsX){
            position.x=  bounds.width() - boundsX;

        }
        if(position.y > bounds.height() - boundsY){
            position.y= bounds.height() - boundsY;

        }
    }
}
