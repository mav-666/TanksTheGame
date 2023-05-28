package com.game.code.BattleFieldFineVersion;

import com.badlogic.gdx.math.Vector2;
import com.game.code.utils.box2d.BodyHandler;

public interface ObstacleFactory {

    Obstacle create(BodyHandler bodyHandler, Vector2 pos, float width, float height);
}
