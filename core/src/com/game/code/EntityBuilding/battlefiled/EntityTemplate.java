package com.game.code.EntityBuilding.battlefiled;

public class EntityTemplate {

    private String entityName;
    private String id;

    private int width = 1;
    private int height = 1;

    public EntityTemplate(String entityName, String id) {
        this.entityName = entityName;
        this.id = id;
    }

    public EntityTemplate(String entityName) {
        this(entityName, "");
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
