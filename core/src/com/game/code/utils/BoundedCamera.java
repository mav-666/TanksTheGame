package com.game.code.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

//камера привязанная к объекту, не выходит за границы карты
public class BoundedCamera extends OrthographicCamera {
    
    private Bounds bounds;
    private float boundsX, boundsY;

    public BoundedCamera(Bounds bounds) {
        setBounds(bounds);
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    @Override
    public void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.U)) zoom += 0.1;
        if(Gdx.input.isKeyPressed(Input.Keys.O)) zoom -= 0.1;

        boundsX = viewportWidth/2 + bounds.startX();
        boundsY = viewportHeight/2 + bounds.startX();

       checkBorderCrossed();

       super.update();
    }
    //красивое перемещение камеры


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
