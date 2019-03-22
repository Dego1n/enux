package com.gameserver.template;

public class NPC {
    private int id;
    private int templateId;
    private String name;
    private int collisionHeight;
    private int collisionRadius;

    private boolean isFriendly;

    public NPC(int id, int templateId, String name, boolean isFriendly, int collisionHeight, int collisionRadius) {
        this.id = id;
        this.templateId = templateId;
        this.name = name;
        this.isFriendly = isFriendly;
        this.collisionHeight = collisionHeight;
        this.collisionRadius = collisionRadius;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCollisionHeight() {
        return collisionHeight;
    }

    public void setCollisionHeight(int collisionHeight) {
        this.collisionHeight = collisionHeight;
    }

    public int getCollisionRadius() {
        return collisionRadius;
    }

    public void setCollisionRadius(int collisionRadius) {
        this.collisionRadius = collisionRadius;
    }

    public boolean isFriendly() {
        return isFriendly;
    }

    public void setFriendly(boolean friendly) {
        isFriendly = friendly;
    }
}
