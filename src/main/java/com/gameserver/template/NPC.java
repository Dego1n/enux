package com.gameserver.template;

import com.gameserver.model.actor.npc.LootTableData;

public class NPC {
    private final int id;
    private final int templateId;
    private final String name;
    private final int collisionHeight;
    private final int collisionRadius;

    private final int respawnTime;

    private final double hp;

    private final boolean isFriendly;

    private final int baseExperience;

    private final LootTableData lootTableData;

    public NPC(int id, int templateId, String name, boolean isFriendly, int collisionHeight, int collisionRadius, double hp, int respawnTime, int baseExperience, LootTableData lootTableData) {
        this.id = id;
        this.templateId = templateId;
        this.name = name;
        this.isFriendly = isFriendly;
        this.collisionHeight = collisionHeight;
        this.collisionRadius = collisionRadius;
        this.hp = hp;
        this.respawnTime = respawnTime;
        this.baseExperience = baseExperience;
        this.lootTableData = lootTableData;
    }

    public int getId() {
        return id;
    }

    public int getTemplateId() {
        return templateId;
    }

    public String getName() {
        return name;
    }

    public int getCollisionHeight() {
        return collisionHeight;
    }

    public int getCollisionRadius() {
        return collisionRadius;
    }

    public boolean isFriendly() {
        return isFriendly;
    }

    public double getHp() {
        return hp;
    }

    public int getRespawnTime() {
        return respawnTime;
    }

    public int getBaseExperience() {
        return baseExperience;
    }

    public LootTableData getLootTableData() {
        return lootTableData;
    }
}
