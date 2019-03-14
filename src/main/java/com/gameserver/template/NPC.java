package com.gameserver.template;

public class NPC {
    private int id;
    private int templateId;
    private String name;

    private boolean isFriendly;

    public NPC(int id, int templateId, String name, boolean isFriendly) {
        this.id = id;
        this.templateId = templateId;
        this.name = name;
        this.isFriendly = isFriendly();
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

    public boolean isFriendly() {
        return isFriendly;
    }

    public void setFriendly(boolean friendly) {
        isFriendly = friendly;
    }
}
