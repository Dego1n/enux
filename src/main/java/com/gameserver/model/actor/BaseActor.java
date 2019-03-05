package com.gameserver.model.actor;

import com.gameserver.database.staticdata.Race;
import com.gameserver.factory.idfactory.ActorIdFactory;

public abstract class BaseActor {

    protected int objectId;
    protected int id;
    private int locationX;
    private int locationY;
    private int locationZ;
    private String name;

    private Race race;

    private int templateId;

    public BaseActor()
    {
        objectId = ActorIdFactory.getInstance().getFreeId();
    }

    public int getObjectId() {
        return objectId;
    }

    public int getId() {
        return id;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public int getLocationZ() {
        return locationZ;
    }

    public void setLocationZ(int locationZ) {
        this.locationZ = locationZ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }
}
