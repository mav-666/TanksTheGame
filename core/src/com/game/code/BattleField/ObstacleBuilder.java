package com.game.code.BattleField;

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

    private void buildObstacles() {
        float pastFreeSpace = getFreeSpace().keySet().size();
        float occupiedSpace = getFreeSpace().keySet().size() * density;


        while(getFreeSpace().keySet().size() + occupiedSpace > pastFreeSpace) {
            addObstacle();
        }

        getBattleField().addActor(obstacles);
    }

    protected abstract void addObstacle();
}
