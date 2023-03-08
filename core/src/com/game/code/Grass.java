package com.game.code;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.game.code.Entity.Wall;

import java.awt.*;

public class Grass extends Actor implements Wall {
    Body body;

    public Grass(World world, Polygon polygon) {

    }
    
    @Override
    public Body getBody() {
        return null;
    }
}
