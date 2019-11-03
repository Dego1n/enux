package com.gameserver.template.stats;

/*
 * @author Dego1n
 * Copyright (c) 22.03.2019
 */

import com.gameserver.database.staticdata.Race;

public class BaseStats {

    private final Race race;

    private int collisionHeight;
    private int collisionRadius;

    public BaseStats(Race race)
    {
        this.race = race;
    }

    public Race getRace() {
        return race;
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
}
