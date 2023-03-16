package com.game.code;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class TileMapBattleField extends BattleField {
    private TiledMap map;

    public TileMapBattleField(World world, TiledMap map, float width, float height) {
        super(world, width, height);

        this.map = map;

    }

    private void addAllMapObjects() {
        for (MapObject object : map.getLayers().get("objects").getObjects()) {
            if (object instanceof PolygonMapObject)
                addObject((PolygonMapObject) object);
        }
    }

    private void addObject(PolygonMapObject object) {
        Polygon polygon = object.getPolygon();

        PolygonShape shape = new PolygonShape();

        shape.set(polygon.getVertices());

    }

}
