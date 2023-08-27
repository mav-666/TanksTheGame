package com.game.code.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;

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
        boundsX = viewportWidth/2 + bounds.startX();
        boundsY = viewportHeight/2 + bounds.startX();

       checkBorderCrossed();

       super.update();
    }

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
