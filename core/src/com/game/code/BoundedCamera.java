package com.game.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;

//камера привязанная к объекту, не выходит за границы карты
public class BoundedCamera extends OrthographicCamera{
    //цель
    private Actor target;

    //размеры пространства
    private float boundariesX, boundariesY, boundariesWidth, boundariesHeight;

    public BoundedCamera(float bWidth, float bHeight) {
        setBorders(bWidth, bHeight);
    }

    //привязать объект
    public void attach(Actor target) {
        this.target = target;
    }

    //отвязать объект
    public void detach(Actor target) {
        this.target = target;
    }

    //установить размеры пространства
    public void setBorders(float width, float height){
        boundariesWidth= width;
        boundariesHeight= height;
    }
    //обновить положение относительно границ и объекта
    @Override
    public void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.U)) zoom += 0.1;
        if(Gdx.input.isKeyPressed(Input.Keys.O)) zoom -= 0.1;

        boundariesX= viewportWidth/2;
        boundariesY= viewportHeight/2;

        if(target != null)
            lerpToTarget();

       checkBorderCrossed();

        super.update();


    }
    //красивое перемещение камеры
    private void lerpToTarget(){
        this.position.x= position.x + (target.getX() + target.getWidth()/2 - position.x) * .05f;
        this.position.y= position.y + (target.getY() + target.getHeight()/2 - position.y) * .05f;
    }

    //проверить пересечение границ
    private void checkBorderCrossed(){
        if(position.x < boundariesX){
            position.x= boundariesX;
        }
        if(position.y < boundariesY){
            position.y= boundariesY;

        }
        if(position.x > boundariesWidth - boundariesX){
            position.x=  boundariesWidth - boundariesX;

        }
        if(position.y > boundariesHeight - boundariesY){
            position.y= boundariesHeight - boundariesY;

        }
    }
}
