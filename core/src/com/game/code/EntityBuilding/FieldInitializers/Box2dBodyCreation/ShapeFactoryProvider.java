package com.game.code.EntityBuilding.FieldInitializers.Box2dBodyCreation;


import com.badlogic.gdx.physics.box2d.Shape;

public class ShapeFactoryProvider {
    private ShapeFactory polygonFactory;
    private ShapeFactory chainFactory;
    private ShapeFactory circleFactory;

    public ShapeFactory provideBy(String shapeType) {
        return switch (Shape.Type.valueOf(shapeType)) {
            case Polygon -> getPolygonFactory();
            case Chain -> getChainFactory();
            case Circle -> getCircleFactory();
            default -> null;
        };
    }

    private String addCapitalLetter(String shapeType) {
        return shapeType.substring(0,1).toUpperCase() + shapeType.substring(1);
    }

    private ShapeFactory getPolygonFactory() {
        if(polygonFactory == null)
            polygonFactory = new PolygonFactory();
        return polygonFactory;
    }

    private ShapeFactory getChainFactory() {
        if(chainFactory == null)
            chainFactory = new ChainFactory();
        return chainFactory;
    }

    private ShapeFactory getCircleFactory() {
        if(circleFactory == null)
            circleFactory = new CircleFactory();
        return circleFactory;
    }
}
