package com.game.code.BattleField;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

public abstract class ObstacleBuilder extends BattleFiledBuilderDecorator {
    //от 0 до 1, процент плотности относительно текущего свободного места
    private final float density;

    protected Group obstacles;

    ObstacleBuilder(float density, BattleFieldBuilder battleFieldBuilder) {
        super(battleFieldBuilder);

        this.density = density;

        obstacles = new Group();
    }

    @Override
    public void buildBattleField() {
        super.buildBattleField();

        buildObstacles();

    }

    protected void buildObstacles() {
        float pastFreeSpace = getFreeSpace().size();
        float occupiedSpace = getFreeSpace().size() * density;


        while(getFreeSpace().size() + occupiedSpace > pastFreeSpace) {
            addObstacle();
        }
    }

    @Override
    public BattleField createBattleField() {
        BattleField battleField = super.createBattleField();

        battleField.addActor(obstacles);

        return battleField;
    }

    protected abstract void addObstacle();

    protected abstract Obstacle createObstacle(Vector2 pos, float width, float height);
}
