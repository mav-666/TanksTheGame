package com.game.code.EntityBuilding.battlefiled;

import com.badlogic.gdx.math.Vector2;

public class BorderPlacer extends AbstractPlacer {

    private final Vector2 currentSpot = new Vector2(bounds.startX(), bounds.startY());
    
    private final Vector2 movingDirection = new Vector2(1, 0);

    private boolean finished = false;

    public BorderPlacer(Bounds bounds) {
        super(bounds);
    }

    @Override
    public Vector2 nextSpot() {
        nextMovingDirection();

        return currentSpot.cpy();
    }

    @Override
    public boolean hasNext() {
        currentSpot.add(movingDirection.x, movingDirection.y);
        return !finished;
    }

    //TODO: rework cause this is not
    private void nextMovingDirection() {
        if(isMovingRight() && currentSpot.x >= bounds.width()) {
            movingDirection.set(0, 1);
        }
        else if(isMovingUp() && currentSpot.y >= bounds.height()) {
            movingDirection.set(-1, 0);
        }
        else if(isMovingLeft() && currentSpot.x <= bounds.startX()) {
            movingDirection.set(0, -1);
        }

        else if(isMovingDown() && currentSpot.y <= bounds.startY()+1) {
            finished = true;
        }
    }

    private boolean isMovingRight() {
        return movingDirection.x == 1 && movingDirection.y == 0;
    }
    private boolean isMovingUp() {
        return movingDirection.x == 0 && movingDirection.y == 1;
    }
    private boolean isMovingLeft() {
        return movingDirection.x == -1 && movingDirection.y == 0;
    }
    private boolean isMovingDown() {
        return movingDirection.x == 0 && movingDirection.y == -1;
    }

    @Override
    public void reset(Bounds bounds) {
        super.reset(bounds);

        currentSpot.set(bounds.startX(), bounds.startY());
        
        movingDirection.set(1, 0);

        finished = false;
    }
}
